// A little class for reading HTML documents over the web.
// By default, this class only reads the first PAGESIZE bytes.
// Read documentation, carefully.
// (c) 2000, 2001 duane a. bailey

import java.io.*;
import java.net.*;
import structure.*;
import java.util.Iterator;

public class HTML extends AbstractIterator implements Comparable
{
    protected URL url;
    protected String content;
    protected List linkList;
    protected String link;
    protected int pagesize;
    protected AbstractIterator theIterator;

    public HTML(String l)
    {
        this(l,10*1024);
    }

    public HTML(String l, int ps)
    // pre: l is a valid URL
    // post: returns a structure that references a web object
    {
        pagesize = ps;
        link = l;
        try
        {
            url = new URL(link);
            link = url.toExternalForm();
        } catch (Exception e)
        {
            url = null;
        }
        content = null;
        linkList = null;
    }

    public String content()
        // post: returns a string that consists of the first page of the URL
    {
        return content(pagesize);
    }

    public String content(int pageSize)
        // pre: pageSize >= 0
        // post: reads up to pageSize characters from the remote URL;
        //       returned as a string
    {
        if (content == null)
        {
            content = "";
            try
            {
                URLConnection conn = url.openConnection();
                if (link.startsWith("http:") &&
                    (conn.getContentType().indexOf("text/html") != -1))
                {
                    InputStream in = (InputStream)url.getContent();
                    byte text[] = new byte[1024];
                    do
                    {
                        if (pageSize <= 0) break;
                        int len = in.read(text,0,Math.min(1024,pageSize));
                        if (len == -1) break;
                        pageSize -= len;
                        content = content + new String(text,0,len);
                    } while (true);
                    in.close();
                }
            } catch (Exception e){};
        }
        return content;
    }

    public int compareTo(Object o)
        // post: comparison is based on full URL
    {
        HTML other = (HTML)o;
        return this.link.compareTo(other.link);
    }

    public boolean equals(Object o)
        // post: equals is based on full URL
    {
        HTML other = (HTML)o;
        return this.link.equals(other.link);
    }

    public String toString()
        // post: returns the URL
    {
        return link;
    }

    public boolean hasNext()
    {
        if (theIterator == null)
        {
            theIterator = (AbstractIterator)links();
        }
        return theIterator.hasNext();
    }
    
    public String nextURL()
    {
        return (String)theIterator.next();
    }

    public Object next()
    {
        return theIterator.next();
    }

    public Object get()
    {
        return theIterator.get();
    }

    public void reset()
    {
        theIterator.reset();
    }


    public Iterator links()
        // post: returns a list of http: links found on first page.
        //       cgi-bin scripts not included.
        {       
            return links(pagesize);
        }

    public Iterator links(int pageSize)
        // pre: pageSize >= 0
        // post: returns the http: links found on the page of size
        //      pageSize
    {   
        if (linkList == null)
        {
            linkList = new SinglyLinkedList();
            if (link.startsWith("http:") &&
                -1 == link.indexOf("/cgi-bin/"))
            {
                try {
                    URLConnection conn = url.openConnection();
                    if (conn.getContentType().indexOf("text/html") == -1) return linkList.iterator();
                    InputStream in = (InputStream)url.getContent();
                    byte text[] = new byte[1024];
                    String buffer = "";
                    do
                    {
                        int start, start2, stop, stop2;
                        if (pageSize <= 0) { break;}
                        int len = in.read(text,0,Math.min(1024,pageSize));
                        if (len == -1) break;
                        pageSize -= len;
                        buffer = buffer + new String(text,0,len);
                        do {
                            start = buffer.indexOf("href=\"");
                            start2 = buffer.indexOf("HREF=\"");
                            if (start == -1) start = start2;
                            if (start2 != -1 && start2 < start) start = start2;
                            if (start == -1) {
                                if (buffer.length() >= 6) buffer = buffer.substring(buffer.length()-6);
                                else buffer = "";
                                break;
                            }
                            start = start + 6;
                            stop = buffer.indexOf("\"",start);
                            stop2 = buffer.indexOf('>',start);
                            if (stop == -1) stop = stop2;
                            if (stop2 != -1 && stop2 < stop) stop = stop2;
                            stop2 = buffer.indexOf('#',start);
                            if (stop == -1) stop = stop2;
                            if (stop2 != -1 && stop2 < stop) stop = stop2;
                            if (stop == -1)
                            {
                                buffer = buffer.substring(start-6);
                                break;
                            }
                            String newLink = buffer.substring(start,stop);
                            URL linkURL = new URL(url,newLink);
                            String name = linkURL.toExternalForm();
                            if (name.startsWith("http:"))
                            {
                                linkList.add(name);
                            }
                            buffer = buffer.substring(stop+1);
                        } while (true);
                    } while (true);
                    in.close();
                } catch (Exception e) {};
            }
        }
        return linkList.iterator();
    }

    public static void main(String args[])
    {
        /*
        HTML page = new HTML("http://www.yahoo.com");
        */
        HTML page = new HTML("http://www.yahoo.com",20*1024);
        System.out.println(page.content());
        int i = 0;
        while (page.hasNext())
        {
            System.out.println(i+": "+page.nextURL());
            i++;
        }
    }
}
