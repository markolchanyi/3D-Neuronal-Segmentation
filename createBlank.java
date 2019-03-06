package neural_tracer_classes;

import ij.ImagePlus;

public class createBlank {
	public createBlank(ImagePlus imp1) {
		imp = imp1;
	}	
	ImagePlus imp;
	public ImagePlus outPut;
	public void run() {
		  ImagePlus blnk = imp.duplicate();
		  blnk.setTitle("blank image");	
		  System.out.println("Start Blank");
		  int height = blnk.getStack().getProcessor(2).getHeight();
		  int width = blnk.getStack().getProcessor(2).getWidth();
			 for(int s = 1; s <= blnk.getStackSize(); s++) {
				 for (int ii = 0; ii < height; ii++) {
					 for (int jj = 0; jj < width; jj++) {
						 blnk.getStack().setVoxel(jj, ii, s-1, 0);
					 }
				 }
			 }
			 outPut = blnk;	 
	}
	
	public ImagePlus returnImage() {
		return outPut;
	}
	
}
