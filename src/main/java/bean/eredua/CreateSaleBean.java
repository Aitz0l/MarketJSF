package bean.eredua;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import businessLogic.BLFacade;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;

@Named("createSale")
@RequestScoped
public class CreateSaleBean {
	private String title;
	private String description;
	private Float price;
	private int status;
	private Date pubDate;
	private Part image;
	private String sellerEmail = "seller3@gmail.com";
	private List<SaleStatus> statusList;

	public CreateSaleBean() {
		statusList = new ArrayList<>();
		statusList.add(new SaleStatus(0, "Berria"));
		statusList.add(new SaleStatus(1, "Oso ona"));
		statusList.add(new SaleStatus(2, "Egokia"));
		statusList.add(new SaleStatus(3, "Oso zaharra"));
	}

	public String createSale() {
		try {
			File file = saveImageToTemp();
			BLFacade facade = FacadeBean.getBusinessLogic();

			facade.createSale(title, description, status, price, pubDate, sellerEmail, file);

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Eskaintza sortuta", null));
			title = null;
			description = null;
			price = null;
			pubDate = null;
			status = 0;
		} catch (Exception e) {
			String msg = (e.getMessage() != null) ? e.getMessage() : e.getClass().getSimpleName();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
		}
		return null;
	}

	private File saveImageToTemp() throws IOException {
		if (image == null || image.getSize() == 0) {
			return null;
		}
		String name = Paths.get(image.getSubmittedFileName()).getFileName().toString();
		Path dir = Files.createTempDirectory("market-img");
		Path target = dir.resolve(name);
		try (InputStream in = image.getInputStream()) {
			Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
		}
		return target.toFile();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public Part getImage() {
		return image;
	}

	public void setImage(Part image) {
		this.image = image;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public List<SaleStatus> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<SaleStatus> statusList) {
		this.statusList = statusList;
	}

	public Date getToday() {
		return new Date();
	}
}