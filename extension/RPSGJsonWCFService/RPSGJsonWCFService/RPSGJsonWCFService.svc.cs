using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Channels;
using System.ServiceModel.Web;
using System.Text;

namespace RPSGJsonWCFService
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Service1" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select Service1.svc or Service1.svc.cs at the Solution Explorer and start debugging.
    public class RPSGJsonWCFService : IRPSGJsonWCFService
    {

        public int Register(string UserName, string Password)
        {
            try
            {
                new WCFServiceInstance.RegisterService().Register(UserName, Password);
            }
            catch (Exception ex)
            {
                LogUtil.Error(ex.Message, ex);
                return -1;
            }
            return 0;
            
        }

        public int login(string UserName, string Password)
        {
            try
            {
                new WCFServiceInstance.LoginService().Login(UserName, Password);
            }
            catch (Exception ex)
            {
                LogUtil.Error(ex.Message, ex);
                return -1;
            }
            return 0;
            
        }


        public string[] GetSaveFiles(string UserName)
        {
            try
            {
                return new WCFServiceInstance.GetSaveFilesService().GetSaveFiles(UserName);
            }
            catch (Exception ex)
            {
                LogUtil.Error(ex.Message, ex);
                return new string[] {};
            }
        }


        public int RemoveSaveFile(string UserName, string File)
        {
            try
            {
                return new WCFServiceInstance.RemoveSaveFileService().RemoveSaveFile(UserName, File);
            }
            catch (Exception ex)
            {
                LogUtil.Error(ex.Message, ex);
                return -1;
            }
        }


        public string GetSaveFile(string UserName, string File)
        {
            try
            {
                return new WCFServiceInstance.GetSaveFileService().GetSaveFile(UserName, File);
            }
            catch (Exception ex)
            {
                LogUtil.Error(ex.Message, ex);
                return "";
            }
        }

        public int AddSaveFile(string UserName, string FileName,string Data)
        {
            try
            {
                return new WCFServiceInstance.AddSaveFileService().AddSaveFile(UserName, FileName, Data);
            }
            catch (Exception ex)
            {
                LogUtil.Error(ex.Message, ex);
                return -1;
            }
        }

    }

    public class JsonContentTypeMapper : WebContentTypeMapper
     {
         public override WebContentFormat GetMessageFormatForContentType(string contentType)
         {
             if (contentType == "text/javascript")
             {
                 return WebContentFormat.Json;
             }
             else
             {
                 return WebContentFormat.Default;
             }
         }
     }
}
