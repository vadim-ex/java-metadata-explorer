package com.stelary.metadata.explorer;

import com.stelary.metadata.explorer.probe.reflect.WildcardTypeProbe;
import lombok.var;
import org.apache.commons.cli.*;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

public class Application {

    private final int jdkVersion;
    private final Probe[] probes;

    public Application(int jdkVersion, Probe[] probes) {
        this.jdkVersion = jdkVersion;
        this.probes = probes;
    }

    private static CliOptions parseCommandLine(String[] args) {
        var options = new Options();
        options.addOption(Option.builder("h")
                .longOpt("help")
                .hasArg(false)
                .desc("print help")
                .build());
        options.addOption(Option.builder("A")
                .longOpt("all")
                .hasArg(false)
                .desc("include all metadata")
                .build());
        options.addOption(Option.builder("a")
                .longOpt("annotation")
                .hasArg(false)
                .desc("include annotation metadata")
                .build());
        options.addOption(Option.builder("g")
                .longOpt("generic")
                .hasArg(false)
                .desc("include generic metadata")
                .build());
        options.addOption(Option.builder("s")
                .longOpt("security")
                .hasArg(false)
                .desc("include security metadata")
                .build());
        options.addOption(Option.builder("p")
                .longOpt("package")
                .hasArg(false)
                .desc("include package and module metadata")
                .build());
        options.addOption(Option.builder()
                .longOpt("trace")
                .hasArg(false)
                .desc("(debug) trace probe execution")
                .build());
        options.addOption(Option.builder()
                .longOpt("class")
                .hasArg(false)
                .desc("(debug) include implementation class name in properties")
                .build());
        options.addOption(Option.builder("o")
                .longOpt("output")
                .numberOfArgs(1)
                .argName("filename")
                .desc("output filename (write to stdout if omitted)")
                .build());
        var parser = new DefaultParser();
        var cliOptions = new CliOptions();
        try {
            var arguments = parser.parse(options, args);
            if (arguments.hasOption("h")) {
                help(System.out, options);
                System.exit(0);
            }
            var all = arguments.hasOption("all");
            cliOptions.annotation = arguments.hasOption("annotation") || all;
            cliOptions.generic = arguments.hasOption("generic") || all;
            cliOptions.security = arguments.hasOption("security") || all;
            cliOptions.packageAndModule = arguments.hasOption("package") || all;
            cliOptions.outputFilename = arguments.getOptionValue("output");
            cliOptions.trace = arguments.hasOption("trace");
            cliOptions.className = arguments.hasOption("class");
        } catch (ParseException exception) {
            System.err.printf("*** error *** %s%n", exception.getMessage());
            help(System.err, options);
            System.exit(4);
        }
        return cliOptions;
    }

    private static void help(PrintStream output, Options options) {
        var formatter = new HelpFormatter();
        var printWriter = new PrintWriter(output);
        String cmdLineSyntax = "java-metadata-explorer";
        formatter.printHelp(printWriter, formatter.getWidth(), cmdLineSyntax, null, options,
                formatter.getLeftPadding(), formatter.getDescPadding(), null, true);
        printWriter.flush();
    }

    public void run(String[] args) {
        try {
            System.out.printf("Java version: %s, selected: %s%n", System.getProperty("java.version"), jdkVersion);
            var cliOptions = parseCommandLine(args);
            for (var probe : this.probes) {
                probe.configure(cliOptions);
            }
            Probe.topSort(probes);

            var graph = new Graph();
            var explorer = new MetadataExplorer(graph, cliOptions, probes);
            var foo = new WildcardTypeProbe[1];
            explorer.explore(foo.getClass());

            var printStream = System.out;
            if (cliOptions.outputFilename != null) {
                var outputStream = new FileOutputStream(cliOptions.outputFilename);
                printStream = new PrintStream(outputStream);
            }
            graph.print(printStream);
        } catch (Exception exception) {
            System.err.printf("%n*** error *** %s%n", exception);
            exception.printStackTrace(System.err);
            System.exit(4);
        }
    }
}
