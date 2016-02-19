using Noesis.Javascript;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ScriptEditor
{
    public class ScriptReader
    {
        public JavascriptContext context = new JavascriptContext();

        public ScriptReader()
        {
            context.SetParameter("p", this);
            context.SetParameter("MsgType", typeof(MsgType));
            context.SetParameter("RPGObject", typeof(RPGObject));
            context.Run("this.__proto__ = p");
        }

        private List<string> m_list = new List<string>();

        public System.Collections.ObjectModel.ReadOnlyCollection<string> getCurrentList()
        {
            return new System.Collections.ObjectModel.ReadOnlyCollection<string>(m_list);
        }

        public void read(string FileName)
        {
            m_list.Clear();
            //"E:/Workspaces/MyEclipse 8.5/rpg/android/assets/script/map/s0-1.js"
            if (!System.IO.File.Exists(FileName))
            {
                return;
            }
            using (var sr = new System.IO.StreamReader(FileName, Encoding.UTF8))
            {
                String line;
                while ((line = sr.ReadLine()) != null)
                {

                    //current = new Script();
                    //current.script = line;
                    bool flag = true;
                    try
                    {
                        context.Run(line);
                        flag = false;
                    }
                    catch (Exception)
                    {
                        //throw;
                    }
                    if (flag)
                        m_list.Add(line);
                    //listBox.Items.Add(current);
                }
            } 
            



        }
        public void say(String str, String title)
        {
            m_list.Add("\"" + title + "\"  说： " + str);
            //current.translate = () => "\"" + title + "\"  说： " + str;
        }

        public void move(int step)
        {
            m_list.Add("当前角色移动了" + step + "步");
            //current.translate = () => "当前角色移动了" + step + "步";

        }

        public void move(NPC npc, int step)
        {
            m_list.Add(npc + "移动了" + step + "步");
            //current.translate = () => npc + "移动了" + step + "步";
        }

        public void pause(int step)
        {
            m_list.Add("暂停" + step + "帧");
            //current.translate = () => "暂停" + step + "帧";
        }

        public void faceTo(RPGObject face)
        {
            m_list.Add("当前角色转向至方向" + face);
            //current.translate = () => "当前角色转向至方向" + face;
        }

        public void faceTo(Object obj, RPGObject face)
        {
            m_list.Add(obj + "转向至方向" + face);
            //current.translate = () => obj + "转向至方向" + face;
        }

        public void hideMSG()
        {
            m_list.Add("隐藏对话框");
            //current.translate = () => "隐藏对话框";
        }

        public void showMSG(MsgType type)
        {
            m_list.Add("显示" + type.ToString() + "对话框");
            //current.translate = () => "显示" + type.ToString() + "对话框";
        }
    }
}
