/**
 * This file was automatically generated by the Mule Development Kit
 */
package com.cts.qr;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Connect;
import org.mule.api.annotations.ValidateConnection;
import org.mule.api.annotations.ConnectionIdentifier;
import org.mule.api.annotations.Disconnect;
import org.mule.api.annotations.param.ConnectionKey;
import org.mule.api.ConnectionException;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Processor;

import com.google.zxing.*;
import com.google.zxing.Reader;
import com.google.zxing.client.j2se.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * Cloud Connector
 *
 * @author Santhosh, Kss.
 */
@Connector(name="qrcode", schemaVersion="1.0-SNAPSHOT")
public class QRCodeConnector
{
    /**
     * Configurable
     */
    @Configurable
    private String myProperty;

    /**
     * Set property
     *
     * @param myProperty My property
     */
    public void setMyProperty(String myProperty)
    {
        this.myProperty = myProperty;
    }

    /**
     * Connect
     *
     * @param username A username
     * @param password A password
     * @throws ConnectionException
     */
    @Connect
    public void connect(@ConnectionKey String username, String password)
        throws ConnectionException {
        /*
         * CODE FOR ESTABLISHING A CONNECTION GOES IN HERE
         */
    }

    /**
     * Disconnect
     */
    @Disconnect
    public void disconnect() {
        /*
         * CODE FOR CLOSING A CONNECTION GOES IN HERE
         */
    }

    /**
     * Are we connected
     */
    @ValidateConnection
    public boolean isConnected() {
        return true;
    }

    /**
     * Are we connected
     */
    @ConnectionIdentifier
    public String connectionId() {
        return "001";
    }

    /**
     * Custom processor
     *
     * {@sample.xml ../../../doc/QRCode-connector.xml.sample qrcode:my-processor}
     *
     * @param content Content to be processed
     * @return Some string
     */
    @Processor
    public String myProcessor(String content)
    {
        /*
         * MESSAGE PROCESSOR CODE GOES HERE
         */

        return content;
    }
    
    
    
    /**
     * Custom processor
     *
     * {@sample.xml ../../../doc/QRCode-connector.xml.sample qrcode:readQRCode}
     *
     * @param filePath Content to be processed
     * @return Some string
     */
    @Processor 
    	  public String readQRCode(String filePath)
    	      throws FileNotFoundException, IOException, NotFoundException {
    	
    	
        String charset = "UTF-8"; 
        Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
    	    BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
    	        new BufferedImageLuminanceSource(
    	            ImageIO.read(new FileInputStream(filePath)))));
    	    Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap);
    	    return qrCodeResult.getText();
        //return charset;
    	  }
    /**
    * Custom processor
    *
    * {@sample.xml ../../../doc/QRCode-connector.xml.sample qrcode:createQRCode}
    *
    * @param filePath qrCodeData Content to be processed
    * @param qrCodeData Content to be processed
    * @return Some string
    */
   @Processor
   
   public String createQRCode(String qrCodeData, String filePath)
   	      throws WriterException, IOException {
	   String charset = "UTF-8"; 
	   int qrCodeheight=200;
	   int qrCodewidth=200;
       Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
       hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
   	    BitMatrix matrix = new MultiFormatWriter().encode(
   	        new String(qrCodeData.getBytes(charset), charset),
   	        BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
   	    MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
   	        .lastIndexOf('.') + 1), new File(filePath));
   	    return "success";
   	  }
   
   /**
    * Custom processor
    *
    * {@sample.xml ../../../doc/QRCode-connector.xml.sample qrcode:createBarCode}
    *
    * @param filePath qrCodeData Content to be processed
    * @param BarCodeData Content to be processed
    * @return Some string
    */
   @Processor
   
   public String createBarCode(String BarCodeData, String filePath)
   	      throws WriterException, IOException {
	   

	   int width = 400;
	   int height = 300; // change the height and width as per your requirement

	   // (ImageIO.getWriterFormatNames() returns a list of supported formats)
	   String imageFormat = "png"; // could be "gif", "tiff", "jpeg" 

	   BitMatrix bitMatrix = new QRCodeWriter().encode(BarCodeData, BarcodeFormat.QR_CODE, width, height);
	   MatrixToImageWriter.writeToStream(bitMatrix, imageFormat, new FileOutputStream(new File(filePath)));
   	    return "success";
   	  }
   
   
   
   /**
    * Custom processor
    *
    * {@sample.xml ../../../doc/QRCode-connector.xml.sample qrcode:ReadBarCode}
    *
    * @param filePath qrCodeData Content to be processed
    * @return Some string
 * @throws NotFoundException 
    */
   @Processor
   
   public String ReadBarCode(String filePath)
   	      throws WriterException, IOException,FormatException ,ChecksumException, NotFoundException{
	   InputStream barCodeInputStream = new FileInputStream("file.jpg");
	   BufferedImage barCodeBufferedImage = ImageIO.read(barCodeInputStream);

	   LuminanceSource source = new BufferedImageLuminanceSource(barCodeBufferedImage);
	   BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
	   Reader reader = new MultiFormatReader();
	   Result result = reader.decode(bitmap);

	   return result.getText();
   	  }

}
