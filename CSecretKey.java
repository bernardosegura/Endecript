import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="system")
public class CSecretKey
{
	private String sKey;
	
	public CSecretKey()
	{
		this.sKey = "";
	}
	
	@XmlElement(name="key")
	public String getKey()
	{
		return this.sKey;
	}

	public void setKey(String Key)
	{
		this.sKey = Key;
	}
}
