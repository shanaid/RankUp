import numpy as np
from PIL import Image
from segment_anything import sam_model_registry, SamPredictor

MODEL_TYPE = "vit_h"
MODEL_DICT = {"vit_h": "vit_h_4b8939", "vit_l": "vit_l_0b3195"}
SAM_CHECKPOINT = f"./weights/sam_{MODEL_DICT[MODEL_TYPE]}.pth"
DEVICE = "cuda"


class SAM():
    def __init__(self,model_type:str=MODEL_TYPE,ckpt:str=SAM_CHECKPOINT):
        self.SAM = sam_model_registry[model_type](checkpoint=ckpt)
        self.SAM.to(device=DEVICE)
        self.predictor = SamPredictor(self.SAM)
    
    def make_mask_with_bbox(self,image:Image,input_box:np.array) -> Image:
        image = image2array(image)
        self.predictor.set_image(image)
        mask, _, _ = self.predictor.predict(
            point_coords=None,
            point_labels=None,
            box=input_box[None, :],
            multimask_output=False,
        )
        mask = Image.fromarray(mask[0,:,:])
        return mask
def image2array(image):
    image = np.array(image)
    return image
if __name__ == "__main__":
    sam = SAM()
    
    print("complete")