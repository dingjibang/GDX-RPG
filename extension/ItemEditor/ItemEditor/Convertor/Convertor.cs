using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Data;
using System.Windows.Media.Imaging;

namespace ItemEditor.Convertor
{
        public class ImageConvertor : IValueConverter
        {

            //Dictionary<string, BitmapImage> m_Cache = new Dictionary<string, BitmapImage>();

            public object Convert(object value, Type targetType, object parameter, System.Globalization.CultureInfo culture)
            {
                if (value is string)
                {
                    try
                    {
                        if (parameter as string == "thumb")
                        {
                            //string hdr = "pack://application:,,,/iEasyTrainingCourseTool;component/";
                            string path = (string)value;
                            //path = path.Replace('\\', '/');
                            path = path + ".thumb";
                            BitmapImage bmp = new BitmapImage(new Uri(path, UriKind.RelativeOrAbsolute));
                            return bmp;
                        }
                        else
                        {
                            string curDir = System.IO.Path.GetDirectoryName(System.Reflection.Assembly.GetExecutingAssembly().Location);
                            string path = (string)value;
                            string sPath = System.IO.Path.Combine(curDir, path);

                            BitmapImage bmp = null;
                            //if (m_Cache.ContainsKey(sPath))
                            //{
                            //    bmp = m_Cache[sPath];
                            //}
                            //else
                            //{
                                bmp = new BitmapImage();
                                bmp.BeginInit();
                                bmp.StreamSource = new MemoryStream(System.IO.File.ReadAllBytes(sPath));
                                bmp.CacheOption = BitmapCacheOption.OnLoad;
                                bmp.EndInit();
                                bmp.Freeze();
                                //m_Cache.Add(sPath, bmp);
                            //}
                            return bmp;
                        }

                    }
                    catch (Exception ex)
                    {
                        //LogUtil.Error(ex.Message, ex);
                        return null;
                    }

                }
                return null;
            }

            public object ConvertBack(object value, Type targetType, object parameter, System.Globalization.CultureInfo culture)
            {
                return null;
            }
        }
}
