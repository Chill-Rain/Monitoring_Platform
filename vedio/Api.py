import json
import logging
from multiprocessing import Process
import cv2
import subprocess as sp
from flask import Flask, request

app = Flask(__name__)
#推流标记
sign = True


# 部署于硬件设备中的Api，用于启动与关闭摄像头
@app.route("/getConnect", methods=["GET"])
def getConnect():
    return_dict = {'return_code': '200', 'return_info': '处理成功', 'result': False}
    if request.args is None:
        return_dict['return_code'] = '5004'
        return_dict['return_info'] = '请求参数为空'
        return json.dumps(return_dict, ensure_ascii=False)
    get_data = request.args.to_dict()
    dst = get_data.get('dst')
    if dst == '':
        return json.dumps(return_dict, ensure_ascii=False)
    else:
        Process(target=work, args=(dst,)).start()
        return json.dumps(return_dict, ensure_ascii=False)


@app.route("/connectClose", methods=["GET"])
def connectClose():
    return_dict = {'return_code': '200', 'return_info': '处理成功', 'result': False}
    if request.args is None:
        return_dict['return_code'] = '5004'
        return_dict['return_info'] = '请求参数为空'
        return json.dumps(return_dict, ensure_ascii=False)
    global sign
    sign = False
    print(sign)
    return json.dumps(return_dict, ensure_ascii=False)


def work(dst):
    # 打开本机摄像头
    cap = cv2.VideoCapture(0)
    try:
        readVideo(cap, dst)
    except Exception as e:
        logging.info('打开摄像头异常', e)
    cap.release()
    cv2.destroyAllWindows()


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
    # global pid
    # pid = pipe.pid
    return pipe


def readVideo(cap, dst):
    pipe = rtmp(cap, dst)
    global sign
    while sign:
        ret, frame = cap.read()
        # 开始推流
        pipe.stdin.write(frame.tobytes())


if __name__ == '__main__':
    app.run(debug=False, host='0.0.0.0', port=5000)
