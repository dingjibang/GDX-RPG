using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ItemEditor
{
    public enum equipType
    {
        [EnumDisplayName("鞋子")]
        EQUIP_SHOES,
        [EnumDisplayName("衣服")]
        EQUIP_CLOTHES,
        [EnumDisplayName("武器")]
        EQUIP_WEAPON,
        [EnumDisplayName("饰品1")]
        EQUIP_ORNAMENT1,
        [EnumDisplayName("饰品2")]
        EQUIP_ORNAMENT2,
    }
}
