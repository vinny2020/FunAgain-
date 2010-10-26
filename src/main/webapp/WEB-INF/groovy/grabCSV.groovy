import com.google.appengine.api.datastore.*
import static com.google.appengine.api.datastore.FetchOptions.Builder.*
// query the scripts stored in the datastore
// "savedscript" corresponds to the entity table containing the scripts' text
def query = new Query("AsinWeight_1287606121556")
 
// sort results by descending order of the creation date
//query.addSort("dateCreated", Query.SortDirection.DESCENDING)
 
// filters the entities so as to return only scripts by a certain author
//query.addFilter("author", Query.FilterOperator.EQUAL, params.author)
 
PreparedQuery preparedQuery = datastore.prepare(query)
 
// return only the first 10 results
def entities = preparedQuery.asList( withLimit(100) )
//def entities = preparedQuery.asList( )
//println "hello"
//println "entities size " + entities.inspect()

def itemW
  def shippinW
  def dimensionW
  StringBuilder sb = new StringBuilder()

//

//sout << "asin,shippinW,dimensionW,itemW"
for (stuff in entities ) {
    
   Entity ent = stuff
   sb <<   ent.asin + "," + ent.shippinW + "," + ent.dimensionW + "," + ent.itemW + "\n"
    
}
response.setHeader("Content-disposition", "attachment;filename=asin-weight.csv")
response.contentType = "text/csv"
response.outputStream << sb
