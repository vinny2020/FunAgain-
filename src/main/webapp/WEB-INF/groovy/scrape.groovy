  import com.google.appengine.api.datastore.Entity
  
  def i = 1
  def counter
  def pcounter
  def asin = params.asin 


  out.println "asin: " + asin


  def slurper = new XmlSlurper(new org.ccil.cowan.tagsoup.Parser())
  def url = new URL("http://amazon.co.uk/o/ASIN/" + asin)
  def html = ""
  String value = ""
  def itemW
  def shippinW
  def dimensionW
  
  def entName = params.entName
  log.info("entity name is " + entName)
  def entity = new Entity(entName)
  entity.asin = asin
 
 


  url.withReader {
    reader ->
    html = slurper.parse(reader)
    //value =  html.body[1].div[0].table[1].tbody.tr.td.div.ul[0].li[5]
    value = html

//def mybody =  value.children()
  }



  value.eachLine {
    if (it.contains("Boxed-product Weight")) {

      def matcher = it =~ /Boxed-product Weight\:(.*?g)/
      // println "<br/>what got matched "  + matcher[0][1] + "<br/>"
      if (matcher.size() > 0) {

      // println "<br/>Shipping Weight " + matcher[0][1].trim() +  "<br/>"
      // println "<br/>using url " + url
        
        shippinW = matcher[0][1].trim()
        entity.shippinW = shippinW
      }

    // println  "<br/> line " +  it
    }

    else if (it.contains("Item Weight")) {
      counter = i
    }


    else if (it.contains("Product Dimensions")) {
        pcounter = i
      }

    if (counter != null && i == counter + 2) {

      //println " 2 steps down counter is ${counter} and i is ${i} "  + it
     // println "Item Weight: " + it.trim()
      itemW = it.trim()
      entity.itemW = itemW
      //println i + "  " +  it
    }


    if (pcounter != null && i == pcounter + 2) {

      // println " 2 steps down pcounter is ${pcounter} and i is ${i} "  + it
      if (it.split(";").size() == 2) {
       // println "Dimension Weight: " + it.split(";")[1].trim()
        dimensionW = it.split(";")[1].trim()
        entity.dimensionW = dimensionW
      }

      //println i + "  " +  it
    }


    i++
  }
  
  entity.save()




//println    "value: "  + value.text().contains("Shipping Weight")
// println    "string "  + value.toString()
/*
 println    "class "  + value.getClass()
 println html.body[1].div[0].table.size()
*/



