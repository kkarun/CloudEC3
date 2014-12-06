/**
 * Base Servlet Class for the Bucket consumption 
 */
package edu.bits.cloud.ec3.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import edu.bits.cloud.ec3.util.Constants;

/**
 * @author Arun
 */
public class BaseBucketConsumerServlet extends HttpServlet implements Constants {

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	private String bucketName = null;

	private String region = null;

	/**
	 * Init method for the Servlet container
	 */
	public void init(ServletConfig servletConfig) throws ServletException {
		this.bucketName = servletConfig.getInitParameter(Constants.BUCKET_NAME);
		this.region = servletConfig.getInitParameter(Constants.REGION);
	}

	/**
	 * Service Method to override the doGet and doPost method to handle my
	 * refresh behavior
	 * @throws IOException 
	 */
	public void service(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws IOException {
			List<S3ObjectSummary> summaryList = getBucketSummary();
			String htmlOutput = getHtmlSummary(summaryList);
			httpServletResponse.setContentType("text/html");  
            PrintWriter out = httpServletResponse.getWriter();  
            out.println(htmlOutput);
	}
	
	private String getHtmlSummary(List<S3ObjectSummary> summaryList) {
		StringBuilder sb = new StringBuilder();
		Date date = new Date();
		sb.append(TABLE_START_TAG);
		
		sb.append(TABLE_ROW_START_TAG);
		sb.append(TABLE_DATA_START_W_COLSPAN);
		sb.append(date.toString());
		sb.append(TABLE_DATA_END_TAG);
		sb.append(TABLE_ROW_END_TAG);
		
		sb.append(TABLE_HEADER);

		if (summaryList != null && summaryList.size() > 0) {
			for (S3ObjectSummary summary : summaryList) {
				sb.append(TABLE_ROW_START_TAG);
				sb.append(TABLE_DATA_START_TAG);
				sb.append(summary.getKey());
				sb.append(TABLE_DATA_END_TAG);
				sb.append(TABLE_DATA_START_TAG);
				sb.append(summary.getSize());
				sb.append(TABLE_DATA_END_TAG);
				sb.append(TABLE_ROW_END_TAG);
			}
		} else {
			sb.append(TABLE_ROW_START_TAG);
			sb.append(TABLE_DATA_START_W_COLSPAN);
			sb.append("Error occured/No data to display");
			sb.append(TABLE_DATA_END_TAG);
			sb.append(TABLE_ROW_END_TAG);	
		}
		return sb.toString();
	}

	/**
	 * Reads the Bucket contents from the AWS S3 Bucket
	 * 
	 * @return List of S3 Bucket Objects
	 */
	private List<S3ObjectSummary> getBucketSummary() {
		
		AmazonS3 amazonS3Client = new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider());
		Region region = Region.getRegion(Regions.fromName(this.region));
		amazonS3Client.setRegion(region);
		
		ObjectListing objectListing = null;
		try {
			objectListing = amazonS3Client.listObjects(new ListObjectsRequest()
					.withBucketName(this.bucketName));
		} catch (AmazonServiceException ase) {
			System.out
					.println("Caught an AmazonServiceException, which means your request made it "
							+ "to Amazon S3, but was rejected with an error response for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out
					.println("Caught an AmazonClientException, which means the client encountered "
							+ "a serious internal problem while trying to communicate with S3, "
							+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}
		
		return objectListing!= null ? objectListing.getObjectSummaries() : null;
	}
}
/* Servlet implementation EOF */