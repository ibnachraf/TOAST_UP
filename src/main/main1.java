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
                = JtwigTemplate.classpathTemplate("index.html",conf);
        JtwigModel model = JtwigModel.newModel()
                .with("filiere","holaaaaaaaaaa tuuuuuu2");
        String output = template.render(model);


        try{
            PrintWriter writer = new PrintWriter("index1.html", "UTF-8");
            writer.write(output);
            writer.close();
        } catch (IOException e) {
            // do something
        }


        String cmd = "wkhtmltopdf --enable-javascript --debug-javascript /home/h-raf/IdeaProjects/T/index1.html pdf";
        String out = executeCommand(cmd);
        String cmd2 = "rm indexo.html";
        executeCommand(cmd2);
        System.out.println(out);
        


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
