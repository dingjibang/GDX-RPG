using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ItemEditor
{
    public enum forwardType
    {
        [EnumDisplayName("对所有单位使用的")]
        all,
        [EnumDisplayName("对我方队友使用的")]
        friend,
        [EnumDisplayName("对敌人使用的")]
        enemy,
        [EnumDisplayName("对与自己连携的对象使用的")]
        link,
    }
}
