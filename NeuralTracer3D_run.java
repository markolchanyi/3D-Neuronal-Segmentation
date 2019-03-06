package neural_tracer_classes;

import java.util.ArrayList;

import org.bytedeco.javacpp.opencv_core;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import ij.ImagePlus;
import io.github.joheras.Point3d;
import net.imagej.ImageJ;

import neural_tracer_classes.canny2d;
import neural_tracer_classes.dilate2d;
import neural_tracer_classes.fillSomas;
import neural_tracer_classes.findSomas;
import neural_tracer_classes.skeletonize3d;
import neural_tracer_classes.twoComponentLabelling3d;


/**
*
* @author Mark
*/
@Plugin(type = Command.class, headless = true, menuPath = "Plugins>IJ-OpenCV-plugins>Neural Tracer_Run")
public class NeuralTracer3D_run implements Command {	  
	  @Parameter
	  private ImagePlus imp;	    
	  @Override
	  public void run() {			  
		  ImagePlus dup2 = imp.duplicate();
		  
		  createBlank blnk = new createBlank(imp);
		  blnk.run();
		  ImagePlus outBlank = blnk.returnImage();
		  
		  findSomas fcc = new findSomas(imp);
		  fcc.run();		  
		  opencv_core.KeyPointVectorVector soma_points = fcc.getKPVV();
		  System.out.println("soma points: " + soma_points.size());
		  
		  
		  canny2d c2d = new canny2d(imp);
		  c2d.run();
		  ImagePlus out = c2d.returnImage();
	      
	      ArrayList<Point3d> soma_pts_to_trace=new ArrayList<Point3d>();	
	      clusterSomas clstr = new clusterSomas(soma_points,imp.getStack().getSize());
	      clstr.run();
	      soma_pts_to_trace = clstr.returnList();	           
	      
	      
	      skeletonize3d skl = new skeletonize3d(out);
	      skl.run();		  	  
		  ImagePlus outCopy = skl.returnImage();
		  
		  
		  dilate2d dlt = new dilate2d(outCopy);
		  dlt.run();
		  ImagePlus outCopy1 = dlt.returnImage();
		  
		  
	      skeletonize3d skl1 = new skeletonize3d(outCopy1);
	      skl1.run();		  	  
		  ImagePlus outCopy2 = skl.returnImage();
		  
		  outCopy.setTitle("This is the skeleton");
	  
		  ImagePlus copyagain = outCopy2.duplicate(); 
	      fillSomas fcms1 = new fillSomas(copyagain,soma_points);
	      fcms1.run();
	      ImagePlus inp = fcms1.outputImage();	
	      
	      Component_Isolation csii = new  Component_Isolation(outBlank, inp, soma_pts_to_trace);
		  csii.run();
		  ImagePlus outISO = csii.returnImage();
	      
	      twoComponentLabelling3d cmpl = new twoComponentLabelling3d(dup2, outISO, soma_pts_to_trace);
	      cmpl.run();
	      System.out.println("done");
	  }
	  
	  public static void main(final String[] args) throws Exception {
	      final ImageJ ij = new ImageJ();
	      ij.launch(args);
	   }
	

}
