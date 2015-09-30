
import structure5.*; 

//A program that finds the number of links between two websites 
public class WebCrawler{
    
    private QueueVector<Association<HTML,Integer>> queue = new QueueVector<Association<HTML,Integer>>(); 
    private HTML page; 

    public WebCrawler(String url){
	page = new HTML(url,20*1024); 
	
    }
    //search for the targetURL from the page HTML 
    public int search(String targeturl){
	//int count = 0; 
	HTML target = new HTML(targeturl,20*1024); 
	int found = this.readLinks(page,target,0); 
	while(found==-1){
	    Association<HTML,Integer> removed = queue.remove(); 
	    HTML test = removed.getKey(); 
	    int index = removed.getValue(); 
	    System.out.println("index: " + index); 
	    found = this.readLinks(test,target,index+1); 
	    //count ++;
	}
	return found;
    }

    //updates the queue with links of the HTML object 
    //read the links of object
    public int readLinks(HTML object,HTML target, int count){
	System.out.println( object+ " " + count); 
	while(object.hasNext()){
	    HTML newPage = new HTML(object.nextURL(),20*1024);
	    if( queue.contains(new Association<HTML,Integer>(newPage))){ 
		if( newPage.equals(target) ){		    
		    return count; 
		}
		//otherwise, don't add redundant links 
	    }
	    else{
		queue.add(new Association<HTML,Integer>(newPage,count)); 
	    }
	}
	return -1; 
    }

    //is wikipedia not an html webpage?  
    public static void main(String args[]){
	WebCrawler crawler = new WebCrawler("http://www.yahoo.com"); 
	System.out.println( crawler.search("http://autos.yahoo.com/used_cars.html") ); 
    }
    

}

