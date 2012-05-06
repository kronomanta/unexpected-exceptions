using System;
using System.IO;
using System.Linq;
using SourceLinesCounter.Properties;

namespace SourceLinesCounter
{
    class Program
    {
        static void Main(string[] args)
        {
            int skeletonLines = 0, protoLines = 0, finalLines = 0;

            Console.WriteLine("Searching for java files in {0}", Settings.Default.SkeletonDirectoryPath);
            Console.WriteLine();

            if(!Directory.Exists(Settings.Default.SkeletonDirectoryPath)){
                Console.WriteLine("Directory does not exist...");
            }else
            {
                string[] filePaths = Directory.GetFiles(Settings.Default.SkeletonDirectoryPath, "*.java", SearchOption.AllDirectories);
                foreach (var filePath in filePaths)
                {
                    Console.WriteLine("Processing {0}", Path.GetFileName(filePath));
                    string[] fileContent = File.ReadAllLines(filePath);

                    skeletonLines += fileContent.Where(IsSourceLine).Count();
                }    
            }
            
            

            Console.WriteLine();
            Console.WriteLine("Searching for java files in {0}", Settings.Default.PrototypeDirectoryPath);
            Console.WriteLine();

            if (!Directory.Exists(Settings.Default.PrototypeDirectoryPath))
            {
                Console.WriteLine("Directory does not exist...");
            }else
            {
                string[] filePaths = Directory.GetFiles(Settings.Default.PrototypeDirectoryPath, "*.java", SearchOption.AllDirectories);
                foreach (var filePath in filePaths)
                {
                    Console.WriteLine("Processing {0}", Path.GetFileName(filePath));
                    string[] fileContent = File.ReadAllLines(filePath);

                    protoLines += fileContent.Where(IsSourceLine).Count();
                }    
            }
            
            Console.WriteLine();
            Console.WriteLine("Searching for java files in {0}", Settings.Default.FinalDirectoryPath);
            Console.WriteLine();


            if (!Directory.Exists(Settings.Default.PrototypeDirectoryPath))
            {
                Console.WriteLine("Directory does not exist...");
            }else
            {
                string[] filePaths = Directory.GetFiles(Settings.Default.FinalDirectoryPath, "*.java", SearchOption.AllDirectories);
                foreach (var filePath in filePaths)
                {
                    Console.WriteLine("Processing {0}", Path.GetFileName(filePath));
                    string[] fileContent = File.ReadAllLines(filePath);

                    finalLines += fileContent.Where(IsSourceLine).Count();
                }
            }


            Console.WriteLine();
            Console.WriteLine("Lines of Code:");
            Console.WriteLine("Skeleton: {0}", skeletonLines);
            Console.WriteLine("Prototype: {0}", protoLines);
            Console.WriteLine("Final: {0}", finalLines);
            Console.ReadKey();
        }

        private static bool IsSourceLine(string str)
        {
            str = str.TrimStart(' ');
            if(Settings.Default.CountComments)
            {
                return (str.Length > 0);    
            }

            return (!str.StartsWith("/*") && !str.StartsWith("* ") && !str.StartsWith("//") && str.Length > 0);
        }
    }
}
