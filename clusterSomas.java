package neural_tracer_classes;

import java.util.ArrayList;

import org.bytedeco.javacpp.opencv_core.KeyPointVectorVector;

import io.github.joheras.Point3d;

//This class uses distance-based clustering of the centroids of somas 
//obtained from the find_somas class to find a single COM centroid to use for connected-
//component labelling

public class clusterSomas {
	//constructor requires voxel depth and original keypoint vector of vectors of soma depth 
	public clusterSomas(KeyPointVectorVector soma_points1,int z_dim1) {
		z_dim = z_dim1;
		soma_points = soma_points1;
	}
	public int z_dim;
	public KeyPointVectorVector soma_points;
	public ArrayList<Point3d> soma_pts_to_trace;
	
	 public void run(){
	        ArrayList<Point3d> soma_pts3d = new ArrayList<Point3d>();
	        ArrayList<ArrayList<Point3d>> cluster_somas=new ArrayList<ArrayList<Point3d>>();
	        ArrayList<Point3d> soma_out= new ArrayList<Point3d>();
	        //create Point3d vector
	        for (int i=0;i<z_dim;i++)
	        {
	              for (int j=0;j<soma_points.get(i).size();j++)
	              {
	                     Point3d temp_point=new Point3d((int)soma_points.get(i).get(j).pt().x(),(int)soma_points.get(i).get(j).pt().y(),i);
	                     soma_pts3d.add(temp_point);
	              }
	        }	       
	        //use distance based clustering	       
	        int dist=50;
	        Boolean [] searched = new Boolean [soma_pts3d.size()];
	        for (int i=0;i<soma_pts3d.size();i++)
	        {
	              searched[i]=false;
	        }	  
	        for (int i=0;i<soma_pts3d.size();i++)
	        {
	              if(searched[i]==false)
	              {
	                     int updates=1;
	                     ArrayList<Point3d> cluster_pts= new ArrayList<Point3d>();
	                     cluster_pts.add(soma_pts3d.get(i));
	                     while (updates>0)
	                     {
	                           updates=0;
	                           for (int j=i+1;j<soma_pts3d.size();j++)
	                           {
	                                  if(searched[j]==false)
	                                  {
	                                        for (int k=0;k<cluster_pts.size();k++)
	                                        {
	                                               if(this.distance3d(cluster_pts.get(k),soma_pts3d.get(j))<dist)
	                                               {
	                                                      cluster_pts.add(soma_pts3d.get(j));
	                                                      searched[j]=true;
	                                                      updates=updates+1;
	                                                     break;
	                                               }
	                                        }
	                                  }
	                           }
	                     }
	                    
	                     cluster_somas.add(cluster_pts);
	              }
	        }
	       	       
	        for (int i=0;i<cluster_somas.size();i++)
	        {
	              int x_center=0;
	              int y_center=0;
	              int z_center=0;
	              ArrayList<Point3d> temp_vector=cluster_somas.get(i);
	              if (!temp_vector.isEmpty())  {
	                     for (int j=0;j<temp_vector.size();j++) {
	                            x_center=x_center+temp_vector.get(j).getx();
	                            y_center=y_center+temp_vector.get(j).gety();
	                            z_center=z_center+temp_vector.get(j).getz();
	                     }
	                    
	                     x_center=x_center/temp_vector.size();
	                     y_center=y_center/temp_vector.size();
	                     z_center=z_center/temp_vector.size();
	                     Point3d temp_pt=new Point3d(x_center,y_center,z_center);
	                     soma_out.add(temp_pt);
	              }
	        }	       
	        soma_pts_to_trace = soma_out;
	 }	
	//simple euclidean distance 
	 public int distance3d(Point3d pt1,Point3d pt2)	{
	        int dist=0;	       
	        dist=(int)Math.sqrt(Math.pow(pt1.getx()-pt2.getx(), 2)+Math.pow(pt1.gety()-pt2.gety(), 2)+9*Math.pow(pt1.getz()-pt2.getz(), 2));       
	        return dist;
	  
	 }
	 
	 public ArrayList<Point3d> returnList(){
		 return soma_pts_to_trace;
	 }
	

}
