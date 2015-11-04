using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ItemEditor
{
    class Item
    {
        public String name;
        public String realName;
        public int id;
        public String filePath;
        public String type;
        public bool throwable;
        public String illustration;
        public bool disable;
        public String use;

        public override string ToString()
        {
            return name;
        }
    }
}
