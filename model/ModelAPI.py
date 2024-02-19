import os
import sys
from multiprocessing import Process
import json
import cv2
import numpy as np
import torch
from PIL import Image
from flask import Flask, request

app = Flask(__name__)
# Model列表
models = {}
# 键
keys = []


# smock API
@app.route("/smock", methods=['Get', 'POST'])
def smock():
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
        Process(target=run_smock, args=(dst,)).start()
        return json.dumps(return_dict, ensure_ascii=False)


# 运行smock模型
def run_smock(dst):
    smock_work(dst)


# smock模型工作器
def smock_work(dst):
    cap = cv2.VideoCapture(dst)
    print("已连接到流媒体服务器！")
    while True:
        ret, frame = cap.read()
        frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
        frame = Image.fromarray(np.uint8(frame))
        # 进行检测
        # frame, boolean = yolo.detect_image(frame)
        frame = np.array(frame)
        # RGBtoBGR满足opencv显示格式
        frame = cv2.cvtColor(frame, cv2.COLOR_RGB2BGR)


# API 入口
if __name__ == '__main__':
    print('starting YoloV5 webservice....')
    # 预训练模型文件夹
    models_dir = 'models_train'
    if len(sys.argv) > 1:
        models_dir = sys.argv[1]
    print(f'watching YoloV5 models form {models_dir}')
    for r, d, f in os.walk(models_dir):
        for file in f:
            if '.pt' in file:
                model_name = os.path.splitext(file)[0]
                model_path = os.path.join(r, file)
                print(f' loading model: {file}')
                models[model_name] = torch.hub.load('./yolov5', 'custom', path=model_path, force_reload=True, source='local')
                model = models[model_name]
                model.conf = 0.5
        for key in models:
            keys.append(key)
    app.run(debug=False, host='0.0.0.0', port=5001)
