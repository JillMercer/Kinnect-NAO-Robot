# NAO-Kinect


Java 4 kinect libraries and source code citation: 

*A. Barmpoutis. "Tensor Body: Real-time Reconstruction of the Human Body* 
*and Avatar Synthesis from RGB-D', IEEE Transactions on Cybernetics,* 
*October 2013, Vol. 43(5), Pages: 1347-1356.* 

*A. Barmpoutis. 'Tensor Body: Real-time Reconstruction of the Human Body and Avatar Synthesis from RGB-D', IEEE Transactions on Cybernetics, Special issue on Computer Vision for RGB-D Sensors: Kinect and Its Applications,*
*October 2013, Vol. 43(5), Pages: 1347-1356*

Copyright 2011-2014, Digital Worlds Institute, University of 
Florida, Angelos Barmpoutis.
All rights reserved.


During the first and second commit the libraries for java4kinect were added.

The second commit added the XYZ joint coordinates for the ViewerPanel3D, there are 26 total joints that are able to be tracked:

    public final static int SPINE_BASE=0; 
    public final static int SPINE_MID=1; 
    public final static int NECK=2; 
    public final static int HEAD=3; 
    public final static int SHOULDER_LEFT=4; 
    public final static int ELBOW_LEFT=5; 
    public final static int WRIST_LEFT=6; 
    public final static int HAND_LEFT=7; 
    public final static int SHOULDER_RIGHT=8; 
    public final static int ELBOW_RIGHT=9; 
    public final static int WRIST_RIGHT=10; 
    public final static int HAND_RIGHT=11; 
    public final static int HIP_LEFT=12; 
    public final static int KNEE_LEFT=13; 
    public final static int ANKLE_LEFT=14; 
    public final static int FOOT_LEFT=15; 
    public final static int HIP_RIGHT=16; 
    public final static int KNEE_RIGHT=17; 
    public final static int ANKLE_RIGHT=18; 
    public final static int FOOT_RIGHT=19; 
    public final static int SPINE_SHOULDER=20; 
    public final static int HAND_TIP_LEFT=21; 
    public final static int THUMB_LEFT=22; 
    public final static int HAND_TIP_RIGHT=23; 
    public final static int THUMB_RIGHT=24; 
    public final static int JOINT_COUNT=25; 

All are located within the java4kinect skeleton class on the j4k website.