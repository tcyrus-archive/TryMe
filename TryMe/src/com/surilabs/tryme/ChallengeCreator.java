package com.surilabs.tryme;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class ChallengeCreator extends Activity 
{

	private final int VIDEO_REQUEST_CODE = 138591;
	private final String TAG = "ChallengeCreator";
	String upLoadServerUri = "http://www.suri-labs.com/tryme/upload-video-test.php";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_challenge_creator);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void selectVideo(View v)
	{
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("video/*");

		// Do this if you need to be able to open the returned URI as a stream
		// (for example here to read the image data).
		intent.addCategory(Intent.CATEGORY_OPENABLE);

		Intent finalIntent = Intent.createChooser(intent, "Select challenge video");

		startActivityForResult(finalIntent, VIDEO_REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{

	    if (requestCode == VIDEO_REQUEST_CODE) 
	    {
	        if(resultCode == ChallengeCreator.RESULT_OK)
	        {
	        	Log.i(TAG, "Video recieved from picker");
	        	Uri videoUri = data.getData();
	        	
	        	final String path = getRealPathFromURI(this, videoUri);
	        	
	        	Bitmap bm = getFrame(path);
	        	if(bm == null)
	        		Log.i(TAG, "bitmap is null");
	        	else
	        		Log.i(TAG, "bitmap is not null");
	        	ImageView im = (ImageView)findViewById(R.id.imageView1);
	        	Drawable new_image= new BitmapDrawable(bm);
	        	im.setBackgroundDrawable(new_image);
	        	
//	        	new Thread(new Runnable() 
//	        	{
//                    public void run() 
//                    {                   
//        	        	uploadFile(path);                                                  
//                    }
//	        	}).start();        
            }
	        else if (resultCode == MainActivity.RESULT_CANCELED)
	        {
	            //Write your code if there's no result
	        }
	    }
	}
	
	private String getRealPathFromURI(Context context, Uri contentUri) 
	{
		  Cursor cursor = null;
		  try
		  { 
		    String[] proj = { MediaStore.Images.Media.DATA };
		    cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
		    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		    cursor.moveToFirst();
		    return cursor.getString(column_index);
		  } finally 
		  {
		    if (cursor != null) 
		    {
		      cursor.close();
		    }
		  }
		}
	
	private int uploadFile(String sourceFileUri) 
	{
        
        String fileName = sourceFileUri;        
        Log.i(TAG, "source File: " + sourceFileUri);
        final String uploadFileName = "test";
        
        HttpURLConnection conn = null;
        DataOutputStream dos = null;  
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024; 
        File sourceFile = new File(sourceFileUri); 
         
        if (!sourceFile.isFile()) {
             
             //dialog.dismiss(); 
              
             Log.e("uploadFile", "Source File not exist : " + sourceFileUri);
              
             runOnUiThread(new Runnable() {
                 public void run() {
                     //messageText.setText("Source File not exist :"
                            // +uploadFilePath + "" + uploadFileName);
                 }
             }); 
              
             return 0;
          
        }
        else
        {
             try 
             { 
                  
                   // open a URL connection to the Servlet
                 FileInputStream fileInputStream = new FileInputStream(sourceFile);
                 URL url = new URL(upLoadServerUri);
                  
                 // Open a HTTP  connection to  the URL
                 conn = (HttpURLConnection) url.openConnection(); 
                 conn.setDoInput(true); // Allow Inputs
                 conn.setDoOutput(true); // Allow Outputs
                 conn.setUseCaches(false); // Don't use a Cached Copy
                 conn.setRequestMethod("POST");
                 conn.setRequestProperty("Connection", "Keep-Alive");
                 conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                 conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                 conn.setRequestProperty("uploaded_file", fileName); 
                  
                 dos = new DataOutputStream(conn.getOutputStream());
        
                 dos.writeBytes(twoHyphens + boundary + lineEnd); 
                 dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                                           + fileName + "\"" + lineEnd);
                  
                 dos.writeBytes(lineEnd);
        
                 // create a buffer of  maximum size
                 bytesAvailable = fileInputStream.available(); 
        
                 bufferSize = Math.min(bytesAvailable, maxBufferSize);
                 buffer = new byte[bufferSize];
        
                 // read file and write it into form...
                 bytesRead = fileInputStream.read(buffer, 0, bufferSize);  
                    
                 while (bytesRead > 0) {
                      
                   dos.write(buffer, 0, bufferSize);
                   bytesAvailable = fileInputStream.available();
                   bufferSize = Math.min(bytesAvailable, maxBufferSize);
                   bytesRead = fileInputStream.read(buffer, 0, bufferSize);   
                    
                  }
        
                 // send multipart form data necesssary after file data...
                 dos.writeBytes(lineEnd);
                 dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
        
                 // Responses from the server (code and message)
                 int serverResponseCode = conn.getResponseCode();
                 String serverResponseMessage = conn.getResponseMessage();
                   
                 Log.i("uploadFile", "HTTP Response is : "
                         + serverResponseMessage + ": " + serverResponseCode);
                  
                 if(serverResponseCode == 200){
                      
                     runOnUiThread(new Runnable() {
                          public void run() {
                               
                              String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                                            +" http://www.androidexample.com/media/uploads/"
                                            +uploadFileName;
                              Log.i(TAG, msg);
                              //messageText.setText(msg);
                              Toast.makeText(ChallengeCreator.this, "File Upload Complete.", 
                                           Toast.LENGTH_SHORT).show();
                          }
                      });                
                 }    
                  
                 //close the streams //
                 fileInputStream.close();
                 dos.flush();
                 dos.close();
                   
            } catch (MalformedURLException ex) {
                 
                //dialog.dismiss();  
                ex.printStackTrace();
                 
                runOnUiThread(new Runnable() {
                    public void run() {
                        //messageText.setText("MalformedURLException Exception : check script url.");
                        Toast.makeText(ChallengeCreator.this, "MalformedURLException", 
                                                            Toast.LENGTH_SHORT).show();
                    }
                });
                 
                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
            } catch (Exception e) {
                 
                //dialog.dismiss();  
                e.printStackTrace();
                 
                runOnUiThread(new Runnable() {
                    public void run() {
                        //messageText.setText("Got Exception : see logcat ");
                        Toast.makeText(ChallengeCreator.this, "Got Exception : see logcat ", 
                                Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("Upload file to server Exception", "Exception : "
                                                 + e.getMessage(), e);  
            }
            return 1; 
             
         } // End else block 
       } 

	private Bitmap getFrame(String path) 
	{
	    try 
	    {
	        Bitmap b;
	        MediaMetadataRetriever mRetriever = new MediaMetadataRetriever();
	        mRetriever.setDataSource(path);                    

            b = mRetriever.getFrameAtTime(1000*3, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
	        
	        return b;
	    } catch (Exception e) { return null; }
	}
}
