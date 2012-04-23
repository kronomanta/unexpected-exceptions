using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using System.Text.RegularExpressions;
using System.Globalization;
using JournalHoursCounter.Properties;

namespace JournalHoursCounter
{
    class Program
    {
        private static readonly Regex hoursRegex = new Regex("\\\\journalentry{[^}]*}{(?<hours>[^}]*)}{(?<name>[^}]*)}{[^}]*}", RegexOptions.Compiled | RegexOptions.CultureInvariant);

        static void Main(string[] args)
        {
            Console.WriteLine("Searching for tex files in {0}", Settings.Default.TargetDirectoryPath);
            Console.WriteLine();

            Dictionary<string, double> hoursSummary = new Dictionary<string, double>();
            string[] filePaths = Directory.GetFiles(Settings.Default.TargetDirectoryPath, "*.tex");
            foreach (string filePath in filePaths)
            {
                Console.WriteLine("Processing {0}", Path.GetFileName(filePath));
                string fileContent = File.ReadAllText(filePath);
                foreach (Match match in hoursRegex.Matches(fileContent))
                {
                    string name = match.Groups["name"].Value;
                    double hours = Double.Parse(match.Groups["hours"].Value.Replace(',', '.'), CultureInfo.InvariantCulture);

                    if (hoursSummary.ContainsKey(name))
                        hoursSummary[name] += hours;
                    else
                        hoursSummary.Add(name, hours);
                }
            }

            Console.WriteLine();
            foreach (KeyValuePair<string, double> hoursSummaryLine in hoursSummary.Where(w => !w.Key.Contains(',')).OrderBy(w => w.Key))
            {
                Console.WriteLine("{0} - {1:0.00} hours", hoursSummaryLine.Key, hoursSummaryLine.Value);
            }

            Console.WriteLine();
            foreach (KeyValuePair<string, double> hoursSummaryLine in hoursSummary.Where(w => w.Key.Contains(',')).OrderBy(w => w.Key))
            {
                Console.WriteLine("{0} - {1:0.00} hours", hoursSummaryLine.Key, hoursSummaryLine.Value);
            }

            Console.WriteLine();
            Console.WriteLine("Press any key to continue...");
            Console.ReadKey(true);
        }
    }
}
