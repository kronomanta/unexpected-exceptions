using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Linq;

namespace RatioCounter
{
    class Program
    {
        static void Main()
        {
            var ratio = new Dictionary<string, double>
                            {
                                {"Vajsz", 0},
                                {"Kanyó", 0},
                                {"Biró", 0},
                                {"Tarjáni", 0},
                                {"Magyar", 0}
                            };

            try
            {
                var exePath = Path.GetDirectoryName(System.Reflection.Assembly.GetEntryAssembly().Location);
                
                var texFiles = Directory.GetFiles(exePath, @"*.tex", SearchOption.TopDirectoryOnly)
                    .Where(a=> Path.GetFileName(a).StartsWith("0") || Path.GetFileName(a).StartsWith("1"))
                    .ToArray();

                foreach (var texFile in texFiles)
                {
                    var file = File.ReadAllText(texFile);
                    file = file.Replace("\n","");
                    int journalIdx = file.IndexOf("{journal}");
                    file = file.Substring(journalIdx+9);
                    var journalEntries = file.Split(new[]{@"\journalentry"},30,StringSplitOptions.RemoveEmptyEntries);
                    if(journalEntries.Length>0)
                    {
                        foreach (var journalEntry in journalEntries)
                        {
                            var entry = journalEntry.Split(new[] { "}{" }, 4, StringSplitOptions.None);
                            var hour = Double.Parse(entry[1].Replace(',','.'),CultureInfo.InvariantCulture);
                            var names = entry[2].Split(' ');

                            foreach (string name in names)
                            {
                                if (ratio.ContainsKey(name.Replace(',', ' ')))
                                {
                                    ratio[name] += hour;
                                }
                            }  
                        }
                    }
                }

                Console.WriteLine("Num of tex files: "+texFiles.Length);
                var sum = ratio.Values.Sum();
                foreach (KeyValuePair<string, double> pair in ratio)
                {
                    Console.WriteLine("{0}: {1} --> {2:0.0}%", pair.Key, pair.Value, pair.Value*100/sum);
                }
                Console.WriteLine("Total worktime: {0} hour(s)", sum);
                Console.ReadKey();
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error: "+ex);
            }
        }
    }
}
