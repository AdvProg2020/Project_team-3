import java.util.ArrayList;
import java.util.Date;

public class Book {
	private final String name;
	private final String ISBN;
	private final int pageCount;
	private final Date publicationDate;
	private ArrayList<Author> authors;
	private final BookFormat format;
	private BookStatus status = BookStatus.AVAILABLE;
	
	public BookStatus getStatus() {
		return status;
	}

	void setStatus(BookStatus status) {
		this.status = status;
	}

	{
		authors = new ArrayList<Author>();
	}
//	public Book(String name, String iSBN, int pageCount, Date publicationDate, Author aut1, Author aut2, Author aut3) {
//	public Book(String name, String iSBN, int pageCount, Date publicationDate, Author[] aut) {
	public Book(String name, String iSBN, int pageCount, Date publicationDate, BookFormat format, Author... aut) {
		this.name = name;
		ISBN = iSBN;
		this.pageCount = pageCount;
		this.publicationDate = publicationDate;
		this.format = format;
//		for(int i=0; i<aut.length; i++)
//			authors.add(aut[i]);
		//for each:
		for(Author a: aut)
//			authors.add(a);
			authors.add(new Author(a));
	}

	@Override
	public String toString() {
		return "Book [name=" + name + ", ISBN=" + ISBN + ", pageCount=" + pageCount + ", publicationDate="
				+ publicationDate + ", authors=" + authors + ", format=" + format + ", status=" + status + "]";
	}

	public BookFormat getFormat() {
		return format;
	}

	public String getName() {
		return name;
	}

	public String getISBN() {
		return ISBN;
	}

	public int getPageCount() {
		return pageCount;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}
	
}
