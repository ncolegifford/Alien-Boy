package application;

public class TileInfo {
	public String mType;
	public String mImageURL;
	public Boolean mNoCollide; 
	
	public TileInfo(String type, String imageURL, Boolean NoCollide) {
		mType = type;
		mImageURL = imageURL; 
		mNoCollide = NoCollide; 
	}

	public String getmType() {
		return mType;
	}

	public void setmType(String mType) {
		this.mType = mType;
	}

	public String getmImageURL() {
		return mImageURL;
	}

	public void setmImageURL(String mImageURL) {
		this.mImageURL = mImageURL;
	}

	
}
