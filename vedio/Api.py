import json
import logging
import os
from multiprocessing import Process, Value
import cv2
import subprocess as sp
from flask import Flask, request

app = Flask(__name__)
# 推流
sign = True

# 部署于硬件设备中的Api，用于启动与关闭摄像头
@app.route("/getConnect", methods=["GET"])
def getConnect():
    return_dict = {'code': '200', 'status': 'SUCCESS', 'data': False, 'info': '请求成功'}
    if request.args is None:
        return_dict['code'] = '5004'
        return_dict['info'] = '请求参数为空'
        return json.dumps(return_dict, ensure_ascii=False)
    get_data = request.args.to_dict()
    dst = get_data.get('dst')
    if dst == '':
        return json.dumps(return_dict, ensure_ascii=False)
    else:
        process = Process(target=work, args=(dst,))
        process.start()
        # global pid
        # pid = process.pid
        return json.dumps(return_dict, ensure_ascii=False)


@app.route("/connectClose", methods=["GET"])
def connectClose():
    return_dict = {'code': '200', 'status': 'SUCCESS', 'data': False, 'info': '请求成功'}
    if request.args is None:
        return_dict['code'] = '600'
        return_dict['info'] = '请求参数为空'
        return json.dumps(return_dict, ensure_ascii=False)
    global sign
    sign = False
    # global pid
    # if pid != 0:
    #     os.killpg(pid, 1)
    #     pid = 0
    # else:
    #     return_dict['code'] = '700'
    #     return_dict['info'] = '摄像机已关闭或不存在！'
    #     return json.dumps(return_dict, ensure_ascii=False)
    return json.dumps(return_dict, ensure_ascii=False)


def work(dst):
    # 打开本机摄像头
    cap = cv2.VideoCapture(0)
    try:
        readVideo(cap, dst)
    except Exception as e:
        logging.info('打开摄像头异常', e)
    cap.release()
    # cv2.destroyAllWindows()


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
               # 'rtsp://mc.serverchillrain.asia:8554/push']
    # command = ['ffmpeg',
    #            '-y',
    #            '-f', 'rawvideo',
    #            '-vcodec', 'rawvideo',
    #            '-pix_fmt', 'bgr24',
    #            '-s', "{}x{}".format(width, height),
    #            '-r', str(fps),
    #            '-i', '-',
    #            '-c:v', 'libx264',
    #            '-pix_fmt', 'yuv420p',
    #            '-preset', 'ultrafast',
    #            # '-f', 'flv',
    #            # 'rtsp://mc.serverchillrain.asia:8554/push']
    #            dst]
    pipe = sp.Popen(command, stdin=sp.PIPE)
    return pipe


def readVideo(cap, dst):
    pipe = rtsp(cap, dst)
    # pipe.close()
    global sign
    while sign:
        # global sign
        ret, frame = cap.read()
        # 开始推流
        pipe.stdin.write(frame.tobytes())


if __name__ == '__main__':
    app.run(debug=False, host='0.0.0.0', port=5000)
