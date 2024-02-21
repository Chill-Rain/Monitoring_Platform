# -*- coding:utf-8 -*
from multiprocessing import Process
import json
import cv2
import io
import os
import sys
import torch
from PIL import Image
from flask import Flask, request, Response, make_response
from werkzeug.exceptions import BadRequest
import subprocess as sp

# 创建flask app
app = Flask(__name__)
# Model列表
models = {}
# 标记
pid_list = []
rtmp_site = 'rtmp://mc.serverchillrain.asia:1935'
webRTC_site = 'http://mc.serverchillrain.asia:1935'
rtsp_site = 'rtsp://mc.serverchillrain.asia:8554'


# 模型识别API 使用url中的一段作为参数，这样一个API接口可以当4个用 已完成测试
@app.route("/<api>/<type>", methods=['GET'])
def detect_api(api, type):
    # 返回的json
    return_dict = {'code': '200', 'status': 'SUCCESS', 'data': False, 'info': '请求成功'}
    if request.args is None:
        return_dict['code'] = '600'
        return_dict['info'] = '请求参数为空'
        return json.dumps(return_dict, ensure_ascii=False)
    model = models[api]
    if model is None:
        return_dict['info'] = '错误的API'
        return json.dumps(return_dict, ensure_ascii=False)
    else:
        # 视频识别
        if type == 'live':
            get_data = request.args.to_dict()
            dst = get_data.get('dst')
            if dst is None or '':
                return_dict['info'] = '错误的API'
                return json.dumps(return_dict, ensure_ascii=False)
            # dst = rtsp_site + dst
            # 获取执行进程的pid方便后面用于关闭
            process = Process(target=detect_video, args=(dst, model, api,))
            process.start()
            # 记录pid
            pid_list[api] = process.pid
            return json.dumps(return_dict, ensure_ascii=False)
        # 图像识别
        elif type == 'img':
            file = extract_img_form_request(request)
            img_bytes = file.read()
            results = detect_img(img_bytes, model)
            results.render()
            for img in results.ims:
                RGB_img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
                im_arr = cv2.imencode('.jpg', RGB_img)[1]
                response = make_response(im_arr.tobytes())
                response.headers['Content-Type'] = 'image/jpeg'
            return response


# 关闭
@app.route('/close/<api>', methods=['GET'])
def close(api):
    return_dict = {'code': '200', 'status': 'SUCCESS', 'data': False, 'info': '请求成功'}
    if request.args is None:
        return_dict['code'] = '600'
        return_dict['info'] = '请求参数为空'
        return json.dumps(return_dict, ensure_ascii=False)
    if pid_list[api] is None:
        return_dict['code'] = '5004'
        return_dict['info'] = '目标API不存在或已关闭！'
        return json.dumps(return_dict, ensure_ascii=False)
    # 杀进程
    # os.kill(pid_list[api], __signal=9)
    os.killpg(pid_list[api], __signal=9)
    return json.dumps(return_dict, ensure_ascii=False)


# 重载模型， 已完成测试
@app.route('/reload', methods=['GET'])
def reload():
    return_dict = {'code': '200', 'status': 'SUCCESS', 'data': False, 'info': '请求成功'}
    if request.args is None:
        return_dict['code'] = '600'
        return_dict['info'] = '请求参数为空'
        return json.dumps(return_dict, ensure_ascii=False)
    load_models()
    return json.dumps(return_dict, ensure_ascii=False)


# 从request中解析图片
def extract_img_form_request(request):
    if 'file' not in request.files:
        raise BadRequest('缺少图片文件！')
    file = request.files['file']
    # 文件名为空 非法
    if file.filename == '':
        raise BadRequest('非法的文件名！')
    return file


# 识别图片 已测试
def detect_img(img_bytes, model):
    # 读取图片
    img = Image.open(io.BytesIO(img_bytes))
    # 识别
    result = model(img, size=640)
    return result


# 识别动作 dst流媒体地址 model识别模型 已测试
def detect_video(dst, model, api):
    # 打开流
    cap = cv2.VideoCapture(dst)
    pipe = rtsp(cap, dst + '/' + api)
    while True:
        ret, frame = cap.read()
        if not ret:
            break
        # 逐帧读取 并转换为 PIL可读的图像
        frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
        pil_img = Image.fromarray(frame)
        # 进行识别
        results = model(pil_img, size=640)
        results.render()
        for img in results.ims:
            RGB_img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
            ret, buffer = cv2.imencode('.jpg', RGB_img)
            frame = buffer.tobytes()
            pipe.stdin.write(RGB_img.tobytes())
            # yield (b'--frame\r\n' b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n')
    cap.release()

# 加载模型 已测试
def load_models():
    global models
    models = {}
    global keys
    keys = []
    print('start YoloV5 webservice...')
    # 预训练模型文件夹
    models_dir = 'models_train'
    if len(sys.argv) > 1:
        models_dir = sys.argv[1]
    print(f'read model form {models_dir}...')
    for r, d, f in os.walk(models_dir):
        for file in f:
            if '.pt' in file:
                model_file_name = os.path.splitext(file)[0]
                model_path = os.path.join(r, file)
                print(f'load model: {file}...')
                model_name = model_file_name.split('_')[0]
                models[model_name] = torch.hub.load('./yolov5', 'custom', path=model_path, force_reload=True,
                                                    source='local')
                model = models[model_name]
                model.conf = 0.5

# rtmp管道 已测试
def rtsp(cap, dst):
    fps = int(cap.get(cv2.CAP_PROP_FPS))
    width = int(cap.get(cv2.CAP_PROP_FRAME_WIDTH))
    height = int(cap.get(cv2.CAP_PROP_FRAME_HEIGHT))
    command = ['ffmpeg',
               '-loglevel', 'error',
               '-c',
               '-y',
               '-f', 'rawvideo',
               '-vcodec', 'rawvideo',
               '-pix_fmt', 'bgr24',
               '-s', "{}x{}".format(width, height),
               '-r', str(fps),
               '-i', '-',
               '-c:v', 'libx264',
               '-pix_fmt', 'yuv420p',
               '-preset', 'ultrafast',
               '-rtsp_transport', 'tcp',
               '-f', 'rtsp',
               dst]
    pipe = sp.Popen(command, stdin=sp.PIPE)
    return pipe


# API 入口
if __name__ == '__main__':
    load_models()
    app.run(debug=False, host='0.0.0.0', port=5001)
