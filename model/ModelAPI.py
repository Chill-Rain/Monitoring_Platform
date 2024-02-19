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
# Model键
keys = []
# 标记
sign = True

# 模型识别API 使用url中的一段作为参数，这样一个API接口可以当4个用
@app.route("/<api>/<type>", methods=['GET'])
def detect_api(api, type):
    # 返回的json
    return_dict = {'return_code': '200', 'return_info': '处理成功', 'result': False}
    if request.args is None:
        return_dict['return_code'] = '5004'
        return_dict['return_info'] = '请求参数为空'
        return json.dumps(return_dict, ensure_ascii=False)
    #获取url上的数据
    get_data = request.args.to_dict()
    dst = get_data.get('dst')
    if dst == '':
        return json.dumps(return_dict, ensure_ascii=False)
    else:
        model = models[api]
        Process(target=work, args=(model, type, dst, )).start()
        return json.dumps(return_dict, ensure_ascii=False)

# 工作器 api为功能名， type为工作模式 dst为流媒体地址
def work(model, type, dst):
    if model is None:
        print(f'错误的api！')
    else:
        # 视频识别
        if type == 'live':
            detect_video(dst, model)



# 从request中解析图片
def extract_img_form_request(request):
    if 'file' not in request.files:
        raise BadRequest('缺少图片文件！')
    file = request.files['file']
    # 文件名为空 非法
    if file.filename == '':
        raise BadRequest('非法的文件名！')
    return file


# 识别图片
def detect_img(img_bytes, model):
    # 读取图片
    img = Image.open(io.BytesIO(img_bytes))
    # 识别
    result = model(img, size=640)
    return result

# 识别动作 dst流媒体地址 model识别模型
def detect_video(dst, model):
    # 打开流
    cap = cv2.VideoCapture(dst)
    pipe = rtmp(cap, 'rtmp://mc.serverchillrain.asia:1935/smock')
    global sign
    while sign:
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


def load_models():
    print('启动YoloV5服务...')
    # 预训练模型文件夹
    models_dir = 'models_train'
    if len(sys.argv) > 1:
        models_dir = sys.argv[1]
    print(f'从{models_dir}中读取模型...')
    for r, d, f in os.walk(models_dir):
        for file in f:
            if '.pt' in file:
                model_file_name = os.path.splitext(file)[0]
                model_path = os.path.join(r, file)
                print(f'尝试加载模型文件：{file}...')
                model_name = model_file_name.split('_')[0]
                models[model_name] = torch.hub.load('./yolov5', 'custom', path=model_path, force_reload=True,
                                                    source='local')
                model = models[model_name]
                model.conf = 0.5

        for key in models:
            keys.append(key)

def rtmp(cap, dst):
    fps = int(cap.get(cv2.CAP_PROP_FPS))
    width = int(cap.get(cv2.CAP_PROP_FRAME_WIDTH))
    height = int(cap.get(cv2.CAP_PROP_FRAME_HEIGHT))
    command = ['ffmpeg',
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
               '-f', 'flv',
               dst]
    pipe = sp.Popen(command, stdin=sp.PIPE)
    return pipe

@app.route('/close', methods=['GET'])
def close():
    return_dict = {'return_code': '200', 'return_info': '处理成功', 'result': False}
    if request.args is None:
        return_dict['return_code'] = '5004'
        return_dict['return_info'] = '请求参数为空'
        return json.dumps(return_dict, ensure_ascii=False)
    global sign
    sign = False
    print(sign)
    return json.dumps(return_dict, ensure_ascii=False)
# API 入口
if __name__ == '__main__':
    load_models()
    app.run(debug=False, host='0.0.0.0', port=5001)
