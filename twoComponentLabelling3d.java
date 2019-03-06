package neural_tracer_classes;
import java.util.ArrayList;
import java.util.Random;

import ij.ImagePlus;
import ij.ImageStack;
import ij.process.ImageConverter;
import inra.ijpb.binary.conncomp.FloodFillComponentsLabeling3D;
import io.github.joheras.Point3d;

import ij3d.Image3DUniverse;
import ij3d.Content;

public class twoComponentLabelling3d {
	public twoComponentLabelling3d(ImagePlus blnkINP, ImagePlus inpINP, ArrayList<Point3d> somasINP) {
		blnk = blnkINP;
		inp = inpINP;
		somas = somasINP;
	}
	
	ImagePlus blnk;
	ImagePlus inp;
	ArrayList<Point3d> somas;
	
	public void run(){
		 ImageStack inpStack = inp.getStack();	  	  
		 FloodFillComponentsLabeling3D comp = new FloodFillComponentsLabeling3D(26,16);	  
		 ImageStack outStk = comp.computeLabels(inpStack);	  
		 
		 int iPos;
		 int jPos;
		 int kPos;
		 int Height = outStk.getHeight();
		 int Width = outStk.getWidth();
		 int stackSize = outStk.getSize();
		 Random rnd = new Random();
		 
		 ImageConverter cv = new ImageConverter(blnk);
		 cv.convertToRGB();
		 blnk.updateImage();
		 
		 double VoxelDepth;
		 ArrayList<int[]> rndColor = new ArrayList<int[]>();
		 int[] rdd = {100,160,0};
		 rndColor.add(rdd);
		 int [] rdd1 = {80,10,255};
		 rndColor.add(rdd1);
		 int [] rdd2 = {40,250,250};
		 rndColor.add(rdd2);
		 int [] rdd3 = {190,100,255};
		 rndColor.add(rdd3);
		 int[] rdd4 = {5,80,255};
		 rndColor.add(rdd4);
		 int [] rdd5 = {255,200,40};
		 rndColor.add(rdd5);
		 int [] rdd6 = {14,185,20};
		 rndColor.add(rdd6);
		 int [] rdd7 = {10,40,255};
		 rndColor.add(rdd7);
		 int [] rdd8 = {255,190,110};
		 rndColor.add(rdd8);
		 int [] rdd9 = {174,255,101};
		 rndColor.add(rdd9);
		 int [] rdd10 = {10,255,180};
		 rndColor.add(rdd10);
		 int [] rdd11 = {255,5,1};
		 rndColor.add(rdd11);
		 int [] rdd12 = {255,5,80};
		 rndColor.add(rdd12);
		 int [] rdd13 = {20,5,255};
		 rndColor.add(rdd13);
		 
		 
		 for (int s = 0; s < somas.size(); s++) {
			 iPos = somas.get(s).gety();
			 jPos = somas.get(s).getx();
			 kPos = somas.get(s).getz();
			 VoxelDepth = outStk.getVoxel(jPos, iPos, kPos);
			 for (int l = -4; l < 4; l++) {
				 for (int m = -4; m <= 4; m++) {
					 for (int n = -2; n <= 2; n++) {
						 if (((jPos + m) < (Width-1)) && ((iPos + l) < (Height-1)) && ((kPos + n) < (stackSize - 1))
								&& ((jPos + m) > 0) && ((iPos + l) > 0) && ((kPos + n) > 0) ) {
							 
						    if (outStk.getVoxel(jPos + m, iPos + l, kPos + n) != 0) {
							      VoxelDepth = outStk.getVoxel(jPos, iPos, kPos);
						 }
					 }
				 }
			 }
			 }
				 
			 if (VoxelDepth != 0) {
				 int p = rnd.nextInt(14);
				 int [] clr = rndColor.get(p);
				 for(int k = 0; k < stackSize; k++) {
					 for(int i = 0; i < Height; i++) {
						 for(int j = 0; j < Width; j++) {
							 if(outStk.getVoxel(j, i, k) == VoxelDepth){
								 blnk.getStack().getProcessor(k+1).putPixel(j, i, clr);
							 }
						 }
					 }
				 }	
			 }
		 }
		 
		 blnk.setTitle("Labelled Components");
		 blnk.show();		 
		 
	 }
	 
}
