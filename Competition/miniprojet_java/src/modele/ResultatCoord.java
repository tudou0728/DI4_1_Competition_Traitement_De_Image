package modele;

public class ResultatCoord 
{
	private int cId;
	private String docChemin;
	private String reqNom;
	private String coordinate;
	private float distance;
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	public String getDocChemin() {
		return docChemin;
	}
	public void setDocChemin(String docChemin) {
		this.docChemin = docChemin;
	}
	public String getReqNom() {
		return reqNom;
	}
	public void setReqNom(String reqChemin) {
		this.reqNom = reqChemin;
	}
	public String getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}
	
	public ResultatCoord()
	{
		
	}
}
