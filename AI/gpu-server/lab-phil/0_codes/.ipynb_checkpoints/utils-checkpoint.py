import os
import cv2
import numpy as np
import mediapipe as mp
from PIL import Image

from diffusers import StableDiffusionControlNetPipeline, ControlNetModel, UniPCMultistepScheduler
import torch
from controlnet_aux import OpenposeDetector
from diffusers.utils import load_image

params = {
    "model_folder": "path_to_openpose_models",
    "hand": False,
    "face": False,
    "disable_blending": True,
    "net_resolution": "1024x1024"
}

def get_unique_filename(base_filename):
    if not os.path.exists(base_filename):
        return base_filename
    else:
        base, ext = os.path.splitext(base_filename)
        counter = 1
        new_filename = f"{base}_{counter}{ext}"
        while os.path.exists(new_filename):
            counter += 1
            new_filename = f"{base}_{counter}{ext}"
        return new_filename


def get_pose_estimation(image_path):
    openpose = OpenposeDetector.from_pretrained('lllyasviel/ControlNet')
    image = load_image(image_path)
    image = openpose(image)
    filename = get_unique_filename("../1_datas/pose.png")
    image.save(filename)

get_pose_estimation("../1_datas/depth.png")