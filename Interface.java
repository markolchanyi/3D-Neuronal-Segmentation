package neural_tracer_classes;

import ij.gui.GenericDialog;

public class Interface {
	  int x_vox_size=1;
	  int y_vox_size=1;
	  int z_vox_size=1;
	  
	  int canny_min=70;
	  int canny_max=210;
	  
	  int post_canny_dilation_size=3;
	  
	  int minThreshold=150;
	  int minArea=100;
	  int maxArea=500;
	  float minCircularity=(float)0.2;
	  float minConvexity=(float)0.87;
	  float minIntertiaraction=(float)0.1;
	  
	  int median_blur_size=21;
	  
	  int cluster_somas_dist=50;
	  
	  
	  boolean canny_edge_detection=true;
	  boolean display_canny_edge_detection=true;
	  
	  boolean post_canny_dilation=true;
	  boolean display_post_canny_dilation=true;
	  
	  boolean post_canny_skeletonization=true;
	  boolean display_post_canny_skeletonization=true;
	  
	  boolean median_blur=true;
	  boolean display_median_blur=true;
	  
	  boolean simple_blob_detector=true;
	  boolean fill_somas=true;
	  
	  boolean cluster_somas=true;
	  boolean display_soma_center_pts=true;
	  
	  boolean trace=true;
	  boolean display_trace=true;
	  
	  
	  public Interface()
	  {
		  
	   GenericDialog gd = new GenericDialog("Neural Tracer");
		gd.addMessage("Voxel Dimension(um)");
		gd.addNumericField("voxel x dim", x_vox_size, 1);
	    gd.addNumericField("voxel y dim", y_vox_size, 1);
	    gd.addNumericField("voxel z dim", z_vox_size, 1);
	     
	        
	     gd.addCheckbox("Canny Edge Detection", canny_edge_detection);
	     gd.addToSameRow();
	     gd.addCheckbox("Display Canny Edge", display_canny_edge_detection);
	     gd.addNumericField("Canny Minimum", canny_min, 1);
	     gd.addToSameRow();
	     gd.addNumericField("Canny Maximum", canny_max, 1);
	     
	     
	     gd.addCheckbox("Post Canny Dilation", post_canny_dilation);
	     gd.addToSameRow();
	     gd.addCheckbox("Display Post Canny dilation", display_post_canny_dilation);
	     gd.addNumericField("Post Canny Dilation Size", post_canny_dilation_size, 1);
	     
	    
	     
	     gd.addCheckbox("Post Canny Skeletonization", post_canny_skeletonization);
	     gd.addToSameRow();
	     gd.addCheckbox("Display Post Canny Skeletonization", display_post_canny_skeletonization);
	     
	     
	     gd.addCheckbox("Median Blur", median_blur);
	     gd.addToSameRow();
	     gd.addCheckbox("Display Median Blur", display_median_blur);
	     gd.addNumericField("Median Blur Size", median_blur_size, 1);

	     
	     gd.addCheckbox("Simple Blob Detector", simple_blob_detector);
	     gd.addToSameRow();
	     gd.addCheckbox("Fill Somas", fill_somas);
	     gd.addNumericField("minThreshold", minThreshold, 1);
	     gd.addNumericField("minArea", minArea, 1);
	     gd.addToSameRow();
	     gd.addNumericField("maxArea", maxArea, 1);
	     gd.addNumericField("minCircularity", minCircularity, 1);
	     gd.addNumericField("minConvexity", minConvexity, 1);
	     gd.addNumericField("minIntertiaraction", minIntertiaraction, 1);


	     
	     gd.addCheckbox("Cluster Soma Points", cluster_somas);
	     gd.addToSameRow();
	     gd.addCheckbox("Displauy Soma Center Points", display_soma_center_pts);
	     gd.addNumericField("Soma Cluster Distance", cluster_somas_dist, 1);
	    
	     
	     gd.addCheckbox("Trace", trace);
	     gd.addToSameRow();
	     gd.addCheckbox("Display Trace", display_trace);

	     gd.showDialog();
		  
	  }
	  
	  public int get_x_vox_size()
	  {
		  return this.x_vox_size;
	  }
	  public int get_y_vox_size()
	  {
		  return this.y_vox_size;
	  }
	  public int get_z_vox_size()
	  {
		  return this.z_vox_size;
	  }
	  public int get_canny_min()
	  {
		  return this.canny_min;
	  }
	  public int get_canny_max()
	  {
		  return this.canny_max;
	  }
	  public int get_post_canny_dilation_size()
	  {
		  return this.post_canny_dilation_size;
	  }
	  public int get_median_blur_size()
	  {
		  return this.median_blur_size;
	  }
	  
	  public int get_cluster_somas_dist()
	  {
		  return this.cluster_somas_dist;
	  }
	  public boolean get_canny_edge_detection()
	  {
		  return this.canny_edge_detection;
	  }
	  
	  public boolean get_display_canny_edge_detection()
	  {
		  return this.display_canny_edge_detection;
	  }
	  
	  
	  public boolean get_post_canny_dilation()
	  {
		  return this.post_canny_dilation;
	  }
	  
	  public boolean get_display_post_canny_dilation()
	  {
		  return this.display_post_canny_dilation;
	  }
	  
	  public boolean get_post_canny_skeletonization()
	  {
		  return this.display_post_canny_skeletonization;
	  }
	  
	  public boolean get_display_post_canny_skeletonization()
	  {
		  return this.display_post_canny_skeletonization;
	  }
	  
	  
	  public boolean get_median_blur()
	  {
		  return this.median_blur;
	  }
	  public boolean get_display_median_blur()
	  {
		  return this.display_median_blur;
	  }
	 
	  public boolean get_simple_blob_detector()
	  {
		  return this.simple_blob_detector;
	  }
	  
	  public boolean get_fill_somas()
	  {
		  return this.fill_somas;
	  }
	  
	  public boolean get_cluster_somas()
	  {
		  return this.cluster_somas;
	  }
	  public boolean get_display_soma_center_pts()
	  {
		  return this.display_soma_center_pts;
	  }
	  public boolean get_trace()
	  {
		  return this.trace;
	  }
	  public boolean get_display_trace()
	  {
		  return this.display_trace;
	  }
	  public int get_minThreshold()
	  {
		  return this.minThreshold;
	  }
	  public int get_minArea()
	  {
		  return this.minArea;
	  }
	  public int get_maxArea()
	  {
		  return this.maxArea;
	  }
	  public float get_minCircularity()
	  {
		  return this.minCircularity;
	  }
	  public float get_minConvexity()
	  {
		  return this.minConvexity;
	  }
	  public float get_minIntertiaraction()
	  {
		  return this.minIntertiaraction;
	  }

	  
	  
	  

}
