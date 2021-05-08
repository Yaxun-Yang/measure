package xml;


import org.apache.bcel.generic.DLOAD;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class ClassInfo{
    Element element;
    String className;
    String id;
    String paId;
    int attrNum=0;
    int totAttrNum=-1;
    int opeNum=0;
    int overrideOpeNum=0;
    int totOpeNum=-1;
    int childNum=0;
    int paDeep=-1;
    int depNum=0;
}




public class xmlDealing {
    public static HashMap<String, ClassInfo> map = new HashMap<>();
    public static int countDeep(String id)
    {
        if (map.get(id).paDeep!=-1)
        return map.get(id).paDeep;

        if (map.get(id).paId==null)
        {
            map.get(id).paDeep=0;
        }
        else
        {
            map.get(id).paDeep=countDeep(map.get(id).paId) + 1;
        }
        return map.get(id).paDeep;
    }
    public static int countTotAttrNum(String id)
    {
        if (map.get(id).totAttrNum!=-1)
            return map.get(id).totAttrNum;

        if (map.get(id).paId==null)
        {
            map.get(id).totAttrNum=map.get(id).attrNum;
        }
        else
        {
            map.get(id).totAttrNum=map.get(id).attrNum+countTotAttrNum(map.get(id).paId);
        }
        return map.get(id).totAttrNum;
    }
    public static int countTotOpeNum(String id)
    {
        if (map.get(id).totOpeNum!=-1)
            return map.get(id).totOpeNum;

        if (map.get(id).paId==null)
        {
            map.get(id).totOpeNum=map.get(id).opeNum;
        }
        else
        {
            map.get(id).totOpeNum=map.get(id).opeNum+countTotOpeNum(map.get(id).paId);
        }
        return map.get(id).totOpeNum;
    }
    public static void main(String[] args) {
        try {
            SAXReader sr = new SAXReader();
            Document document = sr.read("src/xml/xml1.xml");
            Element root = document.getRootElement();
            List<Element> packagedElementList = root.elements("packagedElement");
            List<Element> classElementList = new ArrayList<>();
            List<Element> dependencyList = new ArrayList<>();

            for (Element element : packagedElementList) {
                if(element.attributeValue("type")!=null&&element.attributeValue("type").equals("uml:Class"))
                {
                   classElementList.add(element);
                }
                else if(element.attributeValue("type")!=null&&element.attributeValue("type").equals("uml:Dependency"))
                {
                    dependencyList.add(element);
                }
            }

            for (Element element:classElementList)
            {
                ClassInfo classInfo = new ClassInfo();
                classInfo.className = element.attributeValue("name");
                classInfo.element = element;
                classInfo.id = element.attributeValue("id");
                List<Element> ownedAttributeList = element.elements("ownedAttribute");
                classInfo.attrNum+=ownedAttributeList.size();

                List<Element> ownedOperationList = element.elements("ownedOperation");
                classInfo.opeNum+=ownedOperationList.size();
                map.put(classInfo.id, classInfo);
            }

            for (Element element:classElementList)
            {
                String id = element.attributeValue("id");
                String paId = null;
                if(element.element("generalization")!=null)
                {
                    paId=element.element("generalization").attributeValue("general");
                    map.get(paId).childNum++;
                }

                map.get(id).paId = paId;
            }
            for (String id:map.keySet())
            {
                countDeep(id);
                countTotAttrNum(id);
                countTotOpeNum(id);
            }

            for (Element element:dependencyList)
            {
                map.get(element.attributeValue("supplier")).depNum++;
                map.get(element.attributeValue("client")).depNum++;
            }

           int totOperationNum = 0;
           for (String id:map.keySet())
           {
               ClassInfo classInfo = map.get(id);
               totOperationNum+=classInfo.totOpeNum;
               String paId = classInfo.paId;
               Element element=classInfo.element;
               List<Element> ownedOperationList = element.elements("ownedOperation");
               boolean[] ifOverride = new boolean[ownedOperationList.size()];
               Arrays.fill(ifOverride, false);
               while (paId!=null)
               {
                   ClassInfo paClassInfo = map.get(paId);
                   Element paElement = paClassInfo.element;
                   List<Element> paOwnedOperationList = paElement.elements("ownedOperation");
                   int u = 0;
                   for (Element opeElement:ownedOperationList)
                   {
                       if (ifOverride[u])
                           break;
                       String name = opeElement.attributeValue("name");
                       for(Element paOpeElement:paOwnedOperationList)
                       {
                            String paName = paOpeElement.attributeValue("name");
                            if (name.equals(paName))
                            {
                                List<Element> ownedParameterList = opeElement.elements("ownedParameter");
                                List<Element> paOwnedParameterList = paOpeElement.elements("ownedParameter");
                                if (ownedParameterList.size()==paOwnedParameterList.size())
                                {
                                    boolean flag = true;
                                    Element parm,paParm;
                                    for(int i=0;i<ownedParameterList.size();i++)
                                    {
                                        parm = ownedParameterList.get(i);
                                        paParm = paOwnedParameterList.get(i);
                                        if (!parm.attributeValue("name").equals(paParm.attributeValue("name")))
                                        {
                                            flag = false;
                                            break;
                                        }
                                    }
                                    if (flag)
                                    {
                                        map.get(id).opeNum--;
                                        map.get(id).overrideOpeNum++;
                                        ifOverride[u]=true;
                                        break;
                                    }
                                }

                            }
                       }
                       u++;
                   }

                    paId = map.get(paId).paId;
               }
           }


            int cs = 0;
            int noo = 0;
            int noa = 0;
            int wmc = 0;
            for (String id:map.keySet())
            {
                ClassInfo classInfo = map.get(id);
                System.out.println(classInfo.className);
                System.out.println("unit attribution num: "+classInfo.attrNum);
                System.out.println("attribution num: "+classInfo.totAttrNum);
                System.out.println("unit operation num(LK_NOA): "+classInfo.opeNum);
                System.out.println("operation num: "+classInfo.totOpeNum);
                System.out.println("override operation num(LK_NOO): "+classInfo.overrideOpeNum);
                System.out.println("child Num(CK_NOC): "+classInfo.childNum);
                System.out.println("pa deep(CK_DIT): "+classInfo.paDeep);
                System.out.println("dependency num(CK_CBO): "+classInfo.depNum);
                System.out.println("LK_SI:"+(classInfo.overrideOpeNum*(classInfo.paDeep+1)/totOperationNum));
                System.out.println("CK_WMC(C=1):"+(classInfo.opeNum+ classInfo.overrideOpeNum));
                System.out.println("\n");


                cs+=classInfo.totAttrNum+classInfo.totOpeNum;
                noo+=classInfo.overrideOpeNum;
                noa+=classInfo.opeNum;
                wmc+=classInfo.opeNum+ classInfo.overrideOpeNum;

            }

            System.out.println("LK_CS:"+cs);


        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
