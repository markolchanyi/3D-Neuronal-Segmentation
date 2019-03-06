package neural_tracer_classes;

import java.util.ArrayList;

import ij.ImagePlus;
import ij.ImageStack;
import inra.ijpb.binary.conncomp.FloodFillComponentsLabeling3D;
import io.github.joheras.Point3d;

public class Component_Isolation {
	public Component_Isolation(ImagePlus blnkINP, ImagePlus inpINP, ArrayList<Point3d> somasINP) {
		blnk = blnkINP;
		inp = inpINP;
		somas = somasINP;
	}
	
	ImagePlus blnk;
	ImagePlus inp;
	ImagePlus out;
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
		 
		 
		 double VoxelDepth;


		 for(int s = 0; s < somas.size(); s++) {
			 iPos = somas.get(s).gety();
			 jPos = somas.get(s).getx();
			 kPos = somas.get(s).getz();
			 VoxelDepth = outStk.getVoxel(jPos, iPos, kPos);
			 System.out.println("this is voxel depth: " + VoxelDepth);
			 if (VoxelDepth != 0) {
				 for(int k = 0; k < stackSize; k++) {
					 for(int i = 0; i < Height; i++) {
						 for(int j = 0; j < Width; j++) {
							 if(outStk.getVoxel(j, i, k) == VoxelDepth){
								 blnk.getStack().getProcessor(k+1).putPixel(j, i, 255);
							 }
						 }
					 }
				 }	
			 }
		 }
		 
		  dilateForVisualization dltt = new dilateForVisualization(blnk);
		  dltt.run();
		  out = dltt.returnImage();

		 out.setTitle("Obtained Soma Components");	 
	 }
	
	ImagePlus returnImage() {
		return out;
	}
	 
}
