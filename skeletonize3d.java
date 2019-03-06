package neural_tracer_classes;

import ij.ImagePlus;
import ij.ImageStack;
import io.github.joheras.Skel3Dold;

public class skeletonize3d {
	public skeletonize3d(ImagePlus inp) {
		out = inp;
	}
	public ImagePlus out;
	public ImagePlus outputImage;
	
	public void run(){
		 Skel3Dold imSKELtest= new Skel3Dold();
	     imSKELtest.setup(" ", out);  
	     //out.show();

	     imSKELtest.run(out.getProcessor());
	     

		  
	     System.out.println("done skeletonizing");
	     ImageStack cStack = out.getStack();
	     
	     ImagePlus outCopy = new ImagePlus("copy of skeleton", cStack);
		 outputImage = outCopy;
		 
	 }
	
	public ImagePlus returnImage() {
		return outputImage;
	}

}
