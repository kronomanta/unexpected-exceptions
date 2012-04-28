using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using DocumentationLinker.Properties;
using System.Text.RegularExpressions;
using System.Diagnostics;
using System.Threading;

namespace DocumentationLinker
{
    class Program
    {
        private static readonly string Prefix = "% WARNING!\n" +
            "% This file is generated with DocumentationLinker.exe\n" +
            "% Don't do any changes to the file because it will be lost.\n" +
            "\\input{Common}\n" +
            "\\anyag{Continuity Game}\n" +
            "\\datum{" + Settings.Default.DocumentDate + "}\n" +
            "\\begin{document}\n" +
            "\\fedlap\n";

        private const string Suffix = "\n\\end{document}\n";

        private const string TargetFileName = "Final.tex";

        private static readonly Regex removeRegex1 = new Regex(@"\\anyag{[^}]*?}\n", RegexOptions.Compiled | RegexOptions.CultureInvariant);
        private static readonly Regex removeRegex2 = new Regex(@"\\datum{[^}]*?}\n", RegexOptions.Compiled | RegexOptions.CultureInvariant);

        static void Main(string[] args)
        {
            Console.WriteLine("Looking for documents...");
            string[] fileNames = Directory.GetFiles(Settings.Default.TargetDirectoryPath, "*.tex")
                .Where(w => !Path.GetFileName(w).Equals("Common.tex", StringComparison.InvariantCultureIgnoreCase) &&
                    !Path.GetFileName(w).Equals(TargetFileName, StringComparison.InvariantCultureIgnoreCase))
                .OrderBy(w => w)
                .ToArray();

            Console.WriteLine("Linking the final document...");
            string document = String.Join("\n", fileNames.Select(w => File.ReadAllText(w)));
            document = document.Replace("\\input{Common}\n", "");
            document = document.Replace("\\begin{document}\n", "");
            document = document.Replace("\\fedlap\n", "");
            document = document.Replace("\\end{document}\n", "");
            document = removeRegex1.Replace(document, "");
            document = removeRegex2.Replace(document, "");
            document = Prefix + document + Suffix;

            Environment.CurrentDirectory = Settings.Default.TargetDirectoryPath;
            File.WriteAllText(TargetFileName, document);

            Console.WriteLine("Compiling...");

            string targetFileNameWithoutExtension = Path.GetFileNameWithoutExtension(TargetFileName);
            string finalDocumentPath = Path.Combine("Output", targetFileNameWithoutExtension + ".pdf");
            if (File.Exists(finalDocumentPath))
                File.Delete(finalDocumentPath);

            ProcessStartInfo psi = new ProcessStartInfo("pdflatex", "--output-directory=Output " + TargetFileName);
            psi.UseShellExecute = false;
            psi.RedirectStandardOutput = true;

            Process process = Process.Start(psi);
            while (!process.HasExited)
            {
                string output = process.StandardOutput.ReadLine();
                if (Settings.Default.RedirectPdfLatexOutput)
                    Console.WriteLine(output);
                Thread.Sleep(1);
            }

            Console.WriteLine("Cleaning up...");
            File.Delete(Path.Combine("Output", targetFileNameWithoutExtension + ".aux"));
            File.Delete(Path.Combine("Output", targetFileNameWithoutExtension + ".log"));
            if (Settings.Default.DeleteLinkedLatexSource)
                File.Delete(targetFileNameWithoutExtension + ".tex");
        }
    }
}
