
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.contentstream.operator.color.SetNonStrokingColor;
import org.apache.pdfbox.contentstream.operator.color.SetNonStrokingColorN;
import org.apache.pdfbox.contentstream.operator.color.SetNonStrokingColorSpace;
import org.apache.pdfbox.contentstream.operator.color.SetNonStrokingDeviceCMYKColor;
import org.apache.pdfbox.contentstream.operator.color.SetNonStrokingDeviceGrayColor;
import org.apache.pdfbox.contentstream.operator.color.SetNonStrokingDeviceRGBColor;
import org.apache.pdfbox.contentstream.operator.color.SetStrokingColor;
import org.apache.pdfbox.contentstream.operator.color.SetStrokingColorN;
import org.apache.pdfbox.contentstream.operator.color.SetStrokingColorSpace;
import org.apache.pdfbox.contentstream.operator.color.SetStrokingDeviceCMYKColor;
import org.apache.pdfbox.contentstream.operator.color.SetStrokingDeviceGrayColor;
import org.apache.pdfbox.contentstream.operator.color.SetStrokingDeviceRGBColor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.hibernate.hql.internal.classic.HavingParser;


/**
 * This is an example on how to get the colors of text. Note that this will not
 * tell the background, and will only work properly if the text is not
 * overwritten later, and only if the text rendering modes are 0, 1 or 2. In the
 * PDF 32000 specification, please read 9.3.6 "Text Rendering Mode" to know
 * more. Mode 0 (FILL) is the default. Mode 1 (STROKE) will make glyphs look
 * "hollow". Mode 2 (FILL_STROKE) will make glyphs look "fat".
 *
 * @author Ben Litchfield
 * @author Tilman Hausherr
 */
public class PrintTextColor extends PDFTextStripper {
	private static final String EMPTY_STRING = "";
	/**
	 * Instantiate a new PDFTextStripper object.
	 *
	 * @throws IOException
	 *             If there is an error loading the properties.
	 */
	private String page_ignore = null;
	int linenumber = 0;
	private String headline;
	private String email;
	PDColor currentStyle = null;
	boolean summarystart = false;
	boolean summaryend = false;
	boolean edustart = false;
	boolean eduend = false;
	boolean expstart = false;
	boolean expend = false;
	public String usersummary = "";
	public String titcomptextcurrent="";
	public String titcomptextprevious="";
	boolean titlecompanyend=false;
	boolean institutionstatus=false;
	boolean degreeandspecializationstatus=false;
	boolean descriptionend=false;
	int expcounter=0;
	public String getUsersummary() {
		return usersummary;
	}

	public void setUsersummary(String usersummary) {
		this.usersummary = usersummary;
	}

	public List<Education> getEdulist() {
		return edulist;
	}

	public void setEdulist(List<Education> edulist) {
		this.edulist = edulist;
	}

	public List<Experience> getExperiencelist() {
		return experiencelist;
	}

	public void setExperiencelist(List<Experience> experiencelist) {
		this.experiencelist = experiencelist;
	}
	public String usereducation = "";
	public String userexperience = "";
	Experience exp;
	Education edu;
	String summary = "";
	String experience = "";
	int jobtitlenumber = 0;
	int educounter = 0;
	int institutionlinenumber = 0;
	String expdescription = "";
	String exptitle = "";
	String expcompany = "";
	boolean expiscurrent = false;
	boolean exprecordstart = false;
	boolean exprecordend = false;
	String expstartdate = "";
	String expenddate = "";
	// EDU
	String edudescription = "";
	String eduyearstart = "";
	String eduyearend = "";
	String edudegree = "";
	String eduspecialization = "";
	String eduinstitution = "";
	private List<Education> edulist = new ArrayList<>();

	private List<Experience> experiencelist = new ArrayList<>();
	Map<TextPosition, PDColor> textColorMap = new HashMap<TextPosition, PDColor>();
	StringBuilder textBuilder = new StringBuilder();

	public PrintTextColor() throws IOException {
		addOperator(new SetStrokingColorSpace());
		addOperator(new SetNonStrokingColorSpace());
		addOperator(new SetStrokingDeviceCMYKColor());
		addOperator(new SetNonStrokingDeviceCMYKColor());
		addOperator(new SetNonStrokingDeviceRGBColor());
		addOperator(new SetStrokingDeviceRGBColor());
		addOperator(new SetNonStrokingDeviceGrayColor());
		addOperator(new SetStrokingDeviceGrayColor());
		addOperator(new SetStrokingColor());
		addOperator(new SetStrokingColorN());
		addOperator(new SetNonStrokingColor());
		addOperator(new SetNonStrokingColorN());
		page_ignore = "page ";
	}

	/**
	 * This will print the documents data.
	 *
	 * @param args
	 *            The command line arguments.
	 *
	 * @throws IOException
	 *             If there is an error parsing the document.
	 */
	public static void main(String[] args) throws IOException {
		 		
		 PrintTextColor stripper = new PrintTextColor();
		 //PravinKumarProfile.pdf MadhuPawarProfile.pdf SapanSaxenaProfile.pdf
	      stripper.parseDocument("F:\\JUSTMENTOR_WORKSPACE\\pdfparser\\docs\\AshishChoudharyProfile.pdf");
          //stripper.parseDocument("F:\\JUSTMENTOR_WORKSPACE\\pdfparser\\docs\\DheerajSinghProfile.pdf");
        // stripper.parseDocument("F:\\JUSTMENTOR_WORKSPACE\\pdfparser\\docs\\LarryMaschhoffProfile.pdf");
		//stripper.parseDocument("F:\\JUSTMENTOR_WORKSPACE\\pdfparser\\docs\\BhabaniSahuProfile.pdf");
		//System.out.println("Summary = " + stripper.usersummary);
		//System.out.println("Experience = " + stripper.userexperience);
		//System.out.println("Education = " + stripper.usereducation);
	}

	public void parseDocument(String filePath) throws InvalidPasswordException, IOException {
		try (PDDocument document = PDDocument.load(new File(filePath))) {

			setSortByPosition(true);
			setStartPage(0);
			setEndPage(document.getNumberOfPages());
			Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
			writeText(document, dummy);
		}
	}

	private PDColor determineStyleColor(TextPosition textPosition) {

		return textColorMap.get(textPosition);

	}

	@Override
	protected void writeString(String text, List<TextPosition> textPositions) throws IOException {

		if ("summary".equals(text.toLowerCase().trim())) {
			summarystart = true;
			summaryend = false;
		}

		if ("experience".equals(text.toLowerCase().trim())) {
			expstart = true;
			summaryend = true;
		}
		if ("education".equals(text.toLowerCase().trim())) {
			edustart = true;
			expend = true;
		}

		if (text != null && (text.toLowerCase().startsWith("publications") || text.toLowerCase().startsWith("projects ")
				|| text.toLowerCase().startsWith("certifications ") || text.toLowerCase().startsWith("organizations ")
				|| text.toLowerCase().startsWith("test scores ") || text.toLowerCase().startsWith("spacialities ")
				|| text.toLowerCase().startsWith("honors ") || text.toLowerCase().startsWith("interests ")
				|| text.toLowerCase().startsWith("courses ") || text.toLowerCase().startsWith("recommendations ")
				|| text.toLowerCase().startsWith("patents "))) {
			eduend = true;

		}
		PDColor style = null;
		float fontsize = 0;
		for (TextPosition textPosition : textPositions) {
			style = determineStyleColor(textPosition);
			fontsize = determineFontSize(textPosition);
			if (!style.equals(currentStyle)) {

				output.write(style.getComponents().toString());
				currentStyle = style;
				textBuilder = new StringBuilder(EMPTY_STRING);
			}

		}
		

		if (!isPageLine(text)) {
			processSummary(text, style, fontsize);
			processExperience(text, style, fontsize);
			processEducation(text, style, fontsize);
			// lookup summary
			output.write(text);

		}
		linenumber++;
	}

	private boolean isPageLine(String text) {
		return text.toLowerCase().startsWith(page_ignore);
	}

	private float determineFontSize(TextPosition textPosition) {
		return textPosition.getFontSize();

	}

	private void processSummary(String text, PDColor style, float fontsize) {
		if (summarystart && (!"summary".equals(text.toLowerCase().trim())) && !summaryend) {
			usersummary = usersummary + text;
		}
	}

	private void processExperience(String text, PDColor style, float fontsize) {
		if (expstart && (!"experience".equals(text.toLowerCase().trim())) && !expend) {
			if(style.getComponents()[0] == 0.0f && !hasYear1(text) ){
				    expcounter=expcounter+1;
					 titcomptextcurrent=titcomptextcurrent+text;  
					 if(titlecompanyend) {
					 String[] joblinearray = parseJobTitle(titcomptextprevious);
				     if (joblinearray != null && joblinearray.length > 0) {
							exptitle = trimAdvanced(joblinearray[0]);
							if (joblinearray.length > 1) {
								expcompany = trimAdvanced(joblinearray[1]);
								
				     }
				     }		
					} 
				   if(expcounter>=2 && titlecompanyend) {
					    exp.setId(MongoUtils.getUId());
						exp.setSummary(expdescription);
						exp.setTitle(exptitle);
						exp.setCompanyName(expcompany);
						exp.setIscurrent(expiscurrent);
						if (expstartdate != "") {
							exp.setStartDate(expstartdate);
						}
						if (expenddate != "") {
							exp.setEndDate(expenddate);
						}
						// if(exp)
						experiencelist.add(exp);
						expdescription = "";
						exptitle = "";
						expcompany = "";
						expiscurrent = false;
						expstartdate = "";
						expenddate = "";
						exp = null;
						titlecompanyend=false;
				 }
				 exp = new Experience();
			
				
			}
			if (hasYear1(text)) {
				if (!"".equals(text)) {
					String[] dates = text.split("  -  ");
					if ((dates.length > 1) && (dates[1] != null) && ("Present".equals(dates[1].trim()))) {
						expiscurrent = true;
						populateDates(dates);
						titlecompanyend=true;
						titcomptextprevious=titcomptextcurrent;
						titcomptextcurrent="";
					} else if ((dates.length > 1) && (dates[1] != null)) {
						populateDates(dates);
						titlecompanyend=true;
						titcomptextprevious=titcomptextcurrent;
						titcomptextcurrent="";
					}
				}
			} else {
				if(style.getComponents()[0] == 0.6f )    
				     titcomptextcurrent=titcomptextcurrent+text;
			} 
			if (style.getComponents()[0] == 0.4f) {
				expdescription = expdescription +"\n"+ text;
						
			} 
               
		}
				
	}

	private String[] parseJobTitle(String text) {
		String[] result = null;
		int index = text.indexOf(" at ");
		if (index > 0) {
			result=new String[2];
			result[0]=trimAdvanced(text.substring(0,index));
			result[1]=trimAdvanced(text.substring(index+3,text.length()));
		} else {
			result = new String[1];
			result[0] = text;
		}
		return result;
	}

	private void processEducation(String text, PDColor style, float fontsize) {
		setEmail(text);
		setHeadline(text);
		if (edustart && (!"education".equals(text.toLowerCase().trim())) && !eduend && !email.equals(text) && !headline.equals(text)) {
			populateLastExperience();
			//usereducation=usereducation+text;
			if (!(text != null && text.toLowerCase().startsWith("activities and societies"))&&text.length()<90) {
				educounter++;
				if(fontsize!=20.0f && !text.endsWith(".")) {
					populateEduRecord(educounter, text);
				}
			}else {
				  if(hasYear1(text)) {
					  populateEduRecord(educounter, text);
					  institutionstatus=false;
				  }
			}
		}
	 }
	 public void setEmail(String line) {
		  if(linenumber==2) 
			  email=line;
	
	  }
	  public void setHeadline(String line) {
		  if(linenumber==1) 
			  headline=line;
	  }
	private void populateLastExperience() {
		 if (exp != null) {
			 if(titlecompanyend) {
				 String[] joblinearray = parseJobTitle(titcomptextprevious);
			     if (joblinearray != null && joblinearray.length > 0) {
						exptitle = trimAdvanced(joblinearray[0]);
						if (joblinearray.length > 1) {
							expcompany = trimAdvanced(joblinearray[1]);
							
			     }
			     }		
				} 
		    exp=new Experience();
			exp.setId(MongoUtils.getUId());
			exp.setSummary(expdescription);
			exp.setTitle(exptitle);
			exp.setCompanyName(expcompany);
			exp.setIscurrent(expiscurrent);
			if (expstartdate != "") {
				exp.setStartDate(expstartdate);
			}
			if (expenddate != "") {
				exp.setEndDate(expenddate);
			}
			// if(exp)
			experiencelist.add(exp);
			expdescription = "";
			exptitle = "";
			expcompany = "";
			expiscurrent = false;
			expstartdate = "";
			expenddate = "";
			exp = null;
		}
		
	}

	private void populateEduRecord(int educounter, String text) {
			
		if (institutionstatus) {
			// start of year line
			boolean isyear = hasYear(text);
			if (isyear) {
				String years = text.substring(text.lastIndexOf(",") + 1, text.length());
				years = trimAdvanced(years);
				String[] yrArray = years.split("-");
				if(yrArray.length==2) {
				  eduyearstart = trimAdvanced(yrArray[0]);
				  eduyearend = trimAdvanced(yrArray[1].trim());
				}else{if(yrArray.length==1){
					eduyearstart = trimAdvanced(yrArray[0]);
				}
				}
				institutionstatus=false;
			}
			populateDegreeandSpecialization(text, isyear);
			 
			if(edudegree!=""||eduspecialization!=""||eduyearstart!=""||eduyearend!="") {
				edu = new Education();
				populateEdu();
			}else {
				edu = new Education();
				populateEdu();
				eduinstitution = text;	
				institutionstatus=false;
			}
		
			
		} else {
			edu = new Education();
			populateEdu();
			if(eduinstitution=="") {
				eduinstitution = text;
			    institutionstatus=true;
			}
		}

	}

	@Override
	protected void processTextPosition(TextPosition text) {
		super.processTextPosition(text);
		PDColor nonStrokingColor = getGraphicsState().getNonStrokingColor();
		// System.out.println(text+" : "+nonStrokingColor+" :
		// font="+text.getFontSize());
		textColorMap.put(text, nonStrokingColor);
	}

	private void populateDates(String[] dates) {
		String startpart = dates[0].trim();
		// start date
		expstartdate = getDateFromString(startpart.trim());

		String endpart = dates[1].trim();
		if (!endpart.startsWith("Present")) {
			if (endpart.indexOf("(") > 0) {
				endpart = endpart.substring(0, endpart.indexOf("("));
				endpart = trimAdvanced(endpart);
			}
			// end date
			expenddate = getDateFromString(endpart);
		}
	}

	private String trimAdvanced(String value) {

		Objects.requireNonNull(value);

		int strLength = value.length();
		int len = value.length();
		int st = 0;
		char[] val = value.toCharArray();

		if (strLength == 0) {
			return "";
		}

		while ((st < len) && (val[st] <= ' ') || (val[st] == '\u00A0')) {
			st++;
			if (st == strLength) {
				break;
			}
		}
		while ((st < len) && (val[len - 1] <= ' ') || (val[len - 1] == '\u00A0')) {
			len--;
			if (len == 0) {
				break;
			}
		}

		return (st > len) ? "" : ((st > 0) || (len < strLength)) ? value.substring(st, len) : value;
	}

	private String getDateFromString(String input) {
		String result = null;
		if (isMonthandYear(input.trim())) {
			input = "01 " + input;
		} else if (isYearOnly(input.trim())) {
			input = "01 Jan " + input;
		}
		DateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		DateFormat targetFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
		try {
			Date date = formatter.parse(input.trim());
			result = targetFormat.format(date);
			// result=date.toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
   
	private void populateDegreeandSpecialization(String line, boolean isyear) {
		int commacount = getCommaCount(line);
		if (commacount == 1) {
			// if only one comma and year is there, the first part is assumed to be degree
			if (isyear) {
				edudegree = line.split(",")[0];
				eduspecialization = "";
			} else {
				int indexofcomma = line.indexOf(",", 0);
				edudegree = line.substring(0, indexofcomma);
				eduspecialization = line.substring(indexofcomma + 1, line.length());
			}
			institutionstatus=false;
		} else if (commacount > 1) {
			// if only one comma and year is there, the first part is assumed to be degree
			int firstcomma = line.indexOf(",", 0);
			if (isyear) {
				int lastcomma = line.lastIndexOf(",");
				edudegree = line.substring(0, firstcomma);
				eduspecialization = line.substring(firstcomma + 1, lastcomma);
				institutionstatus=false;
			} else {
				edudegree = line.substring(0, firstcomma);
				eduspecialization = line.substring(firstcomma + 1, line.length());
				institutionstatus=false;
			}
			
		}
	}

	private void populateEdu() {
		if (edu != null && eduinstitution!="") {
			edu.setId(MongoUtils.getUId());
			edu.setDegree(edudegree);
			edu.setFieldOfStudy(eduspecialization);
			edu.setSchoolName(eduinstitution);
			edu.setSummary(edudescription);
			if (eduyearstart != null && eduyearstart.trim().length() > 0) {
				edu.setStartDate(getFormattedDate("01/01/" + eduyearstart.replaceAll("[^0-9]", "")));
				eduyearstart = "";
			}
			if (eduyearend != null && eduyearend.trim().length() > 0) {
				edu.setEndDate(getFormattedDate("01/01/" + eduyearend.replaceAll("[^0-9]", "")));
				eduyearend = "";
			}
		 	edulist.add(edu);
			edudegree = "";
			eduspecialization = "";
			eduinstitution = "";
			edudescription = "";
			institutionstatus=false;
			
		}
	}

	private String getFormattedDate(String string) {
		Date date = null;
		DateFormat originalFormat = new SimpleDateFormat("mm/dd/yyyy", Locale.ENGLISH);
		DateFormat targetFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");

		try {
			date = originalFormat.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String formattedDate = targetFormat.format(date); // Fri, 01 Aug 2003 07:00:00 GMT
		return formattedDate;
	}

	private int getCommaCount(String line) {
		int result = 0;
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == ',')
				result++;
		}
		return result;
	}

	private boolean hasYear(String line) {
		String pattern = "^.+?\\d$";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(line);
		return m.matches();
	}
	private boolean hasYear1(String line) {
		line = line.replaceAll("[^0-9]", "#"); //simple solution for replacing all non digits. 
	  	String[] arr = line.split("#");
	    boolean hasYear = false;
	    for(String s : arr){
	        if(s.matches("^[0-9]{4}$")){
	            hasYear = true;
	         }
	    }
	  	return hasYear;
	}
	
	private boolean isYearOnly(String line) {
		String pattern = "^\\d{4}$";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(line);
		return m.matches();
	}

	private boolean isMonthandYear(String line) {
		String pattern = "^(Jan(uary)?|Feb(ruary)?|Mar(ch)?|Apr(il)?|May|Jun(e)?|Jul(y)?|Aug(ust)?|Sep(tember)?|Oct(ober)?|Nov(ember)?|Dec(ember)?)\\s\\d{4}$";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(line);
		return m.matches();
	}

	/**
	 * This will print the usage for this document.
	 */
	private static void usage() {
		System.err.println("Usage: java " + PrintTextColor.class.getName() + " <input-pdf>");
	}
}