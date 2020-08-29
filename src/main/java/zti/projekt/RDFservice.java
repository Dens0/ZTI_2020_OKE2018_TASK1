package zti.projekt;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RDFservice {

    public Optional<String> check(String text)
    {
        List<String> wordTypesList =  getOntologyClass(text);

        Optional<String> foundType = wordTypesList.stream()
                .filter(word -> {
                    if (Ontology.getOntologyClasses().contains(word)) return true;
                    else return false;
                })
                .findFirst();

        return foundType;

    }
    public List<String> getOntologyClass(String text) {
        String queryValue = "PREFIX dbres: <http://dbpedia.org/resource/> PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "select ?o where {dbres:"+ text + " rdf:type ?o}";

        Query query = QueryFactory.create(queryValue);
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query)) {

            ((QueryEngineHTTP) queryExecution).addParam("timeout", "10000");

            ResultSet rs = queryExecution.execSelect();
            List<String> resultList = new ArrayList<>();
            while(rs.hasNext())
            {
                QuerySolution qSolution = rs.nextSolution() ;
                RDFNode rdfNode = qSolution.get("o");
                resultList.add(rdfNode.toString());
            }
            return resultList;
        }
    }
}