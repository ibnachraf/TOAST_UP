package src;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.environment.EnvironmentConfiguration;
import org.jtwig.environment.EnvironmentConfigurationBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Calendar;

/**
 * Created by h-raf on 03/06/17.
 */
public class main1 {

    public static void main (String[] args) throws IOException, InterruptedException {


        EnvironmentConfiguration conf = EnvironmentConfigurationBuilder
                .configuration()
                .resources()
                .absoluteResourceTypes().add("string").and()
                .withDefaultInputCharset(Charset.forName("UTF-8"))
                .and()
                .build();
        JtwigTemplate template
                = JtwigTemplate.classpathTemplate("./assets/fiche.twig.html",conf);
        JtwigModel model = JtwigModel.newModel()
                .with("filiere", Calendar.getInstance().getTime());
        String output = template.render(model);

        toPDF(output);


    }

    private static String toPDF(String output/*,Evaluation eval */){
        String pathHtml = "./HTML/fiche_eval_HTML.html";
        try{
            //String pathHtml = "./HTML/fiche_evaluation_HTML"+eval.getStage().getEtudiant().getIdentEtudiant()+"_"+eval.getStage().getEtudiant().getNomUsuel()+".html";

            PrintWriter writer = new PrintWriter(pathHtml, "UTF-8");
            writer.write(output);
            writer.close();
        } catch (IOException e) {
            // do something
        }


        String path = "./PDF/fiche_evaluation_.pdf";
        String cmd = "wkhtmltopdf --enable-javascript --debug-javascript "+pathHtml+" "+path;
        String out = executeCommand(cmd);
        String cmd2 = "rm index1.html";
        executeCommand(cmd2);
        System.out.println("exec: "+out);

        return path;

    }

    private static String executeCommand(String command) {

        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();

    }

}
