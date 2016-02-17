using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

namespace RPSGJsonWCFService
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IService1" in both code and config file together.
    [ServiceContract]
    public interface IRPSGJsonWCFService
    {

        [OperationContract]///{UserName}/{Password}
        [WebGet( RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json, BodyStyle = WebMessageBodyStyle.Wrapped, UriTemplate = "Register/{UserName}/{Password}")]

        int Register(string UserName, string Password);

       [OperationContract]
       [WebGet(RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json, BodyStyle = WebMessageBodyStyle.Wrapped, UriTemplate = "login/{UserName}/{Password}")]
      int login(string UserName,string Password);

        // TODO: Add your service operations here
       [OperationContract]
       [WebGet(RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json, BodyStyle = WebMessageBodyStyle.Wrapped, UriTemplate = "{UserName}/{Password}/GetSaveFiles")]
       string[] GetSaveFiles(string UserName, string Password);
       [OperationContract]
       [WebGet(RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json, BodyStyle = WebMessageBodyStyle.Wrapped, UriTemplate = "{UserName}/{Password}/RemoveSaveFile/{File}")]
       int RemoveSaveFile(string UserName, string Password, string File);

       [OperationContract]
       [WebGet(RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json, BodyStyle = WebMessageBodyStyle.Wrapped, UriTemplate = "{UserName}/{Password}/GetSaveFile/{File}")]
       string GetSaveFile(string UserName, string Password, string File);

       [OperationContract]
       [WebInvoke(RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json, BodyStyle = WebMessageBodyStyle.Wrapped, UriTemplate = "{UserName}/{Password}/AddSaveFile/{FileName}")]
       int AddSaveFile(string UserName, string Password, string FileName, string Data);
    }


    //// Use a data contract as illustrated in the sample below to add composite types to service operations.
    //[DataContract]
    //public class CompositeType
    //{
    //    bool boolValue = true;
    //    string stringValue = "Hello ";

    //    [DataMember]
    //    public bool BoolValue
    //    {
    //        get { return boolValue; }
    //        set { boolValue = value; }
    //    }

    //    [DataMember]
    //    public string StringValue
    //    {
    //        get { return stringValue; }
    //        set { stringValue = value; }
    //    }
    //}
}
