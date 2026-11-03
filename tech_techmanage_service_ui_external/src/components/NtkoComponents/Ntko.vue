<template>
    <div>
        <div id="ntko">
        </div>
        <!-- <button @click="saveNtkoToUrl()">保存</button> -->
    </div>
</template>
<script>
    import { preview } from '@/api/common/workflowApi'
    export default {
        name: 'Ntko',
        data() {
            return {
                //32位控件的classid
                classid: "B39F1330-3322-4a1d-9BF0-0BA2BB90E970",
                //64位控件的calssid
                //classidx64 : "A64E3073-2016-4baf-A89D-FFE1FAA10EE0",
                //32位控件包的路径
                codebase: "components/ofctnewclsid.cab#version=5,0,2,7",
                //64位控件包的路径
                //codebase64 : "static/OfficeControlx64.cab#version=6,0,0,1",
                //设置高度
                height: "800px",
                //设置宽度
                width: "100%",
                //买断授权密钥如果不是买断可以不用写
                MakerCaption: "",
                //买断授权密钥如果不是买断可以不用写
                MakerKey: "",
                //密钥
                ProductCaption: "XX银行",
                //密钥
                ProductKey: "D9463F103863D4EC0C5908CAA6BD826A93627D72",
                //解除时间密钥
                NoExpireKey: ""

            }
        },
        methods: {
            initActiveXObject() { //例子写ondocumentopened事件
                var scriptStr = document.createElement("script")
                scriptStr.setAttribute("for", "TANGER_OCX")
                scriptStr.event = "OndocumentOpened(str,doc)"
                scriptStr.appendChild(document.createTextNode("activeXListener.wakeUp(str,doc)"))
                document.body.appendChild(scriptStr)
                window.activeXListener = this
            },
            wakeUp(str, doc) { //事件触发回调

                if (this.NTKOGLOBAL.TANGER_OCX_OBJ.getofficever() >= 15) {
                    doc.Application.Options.UseLocalUserInfo = true;
                }
                this.NTKOGLOBAL.TANGER_OCX_OBJ.webusername = "ntko_user";
                doc.trackrevisions = true;//修订状态

            },
            //   saveNtkoToUrl(){
            //      if(this.browser=="IE"){
            //          alert(ret);
            //      }
            //   }

        },
        computed: {
            browser() {
                let userAgent = navigator.userAgent
                let rMsie = /(msie\s|trident.*rv:)([\w.]+)/
                let rFirefox = /(firefox)\/([\w.]+)/
                let rOpera = /(opera).+version\/([\w.]+)/
                let rChrome = /(chrome)\/([\w.]+)/
                let rSafari = /version\/([\w.]+).*(safari)/
                let browser
                function uaMatch(ua) {
                    let match = rMsie.exec(ua);
                    if (match != null) {
                        return { browser: "IE", version: match[2] || "0" };
                    }
                    match = rFirefox.exec(ua);
                    if (match != null) {
                        return { browser: match[1] || "", version: match[2] || "0" };
                    }
                    match = rOpera.exec(ua);
                    if (match != null) {
                        return { browser: match[1] || "", version: match[2] || "0" };
                    }
                    match = rChrome.exec(ua);
                    if (match != null) {
                        return { browser: match[1] || "", version: match[2] || "0" };
                    }
                    match = rSafari.exec(ua);
                    if (match != null) {
                        return { browser: match[2] || "", version: match[1] || "0" };
                    }
                    if (match != null) {
                        return { browser: "", version: "0" };
                    }
                }
                let browserMatch = uaMatch(userAgent.toLowerCase());
                if (browserMatch.browser) {
                    browser = browserMatch.browser;
                }
                return browser
            }
        },

        mounted() {
            this.initActiveXObject();
            this.$nextTick(() => {
                if (this.browser == "IE") {
                    if (window.navigator.platform == "Win32") {
                        let domStr = ""
                        domStr += '<!-- 用来产生编辑状态的ActiveX控件的JS脚本-->   '
                        domStr += '<!-- 因为微软的ActiveX新机制，需要一个外部引入的js-->   '
                        domStr += '<object id="TANGER_OCX" classid="clsid:' + this.classid + '" codebase="' + this.codebase + '" width="' + this.width + '" height="' + this.height + '">   '
                        domStr += '<param name="MakerCaption" value="' + this.MakerCaption + '">   '
                        domStr += '<param name="MakerKey" value="' + this.MakerKey + '">   '
                        domStr += '<param name="ProductCaption" value="' + this.ProductCaption + '">   '
                        domStr += '<param name="ProductKey" value="' + this.ProductKey + '">   '
                        domStr += '<param name="NoExpireKey" value="' + this.NoExpireKey + '">   '
                        domStr += '<param name="IsUseUTF8URL" value="-1">   '
                        domStr += '<param name="IsUseUTF8Data" value="-1">   '
                        domStr += '<param name="Caption" value="NTKO OFFICE文档控件示例演示 http://www.ntko.com">   '
                        domStr += '<SPAN STYLE="color:red">不能装载文档控件。请在检查浏览器的选项中检查浏览器的安全设置。</SPAN>   '
                        domStr += '</object>'
                        document.getElementById('ntko').innerHTML += domStr
                    }
                    if (window.navigator.platform == "Win64") {
                        let domStr = ""
                        domStr += '<!-- 用来产生编辑状态的ActiveX控件的JS脚本-->   '
                        domStr += '<!-- 因为微软的ActiveX新机制，需要一个外部引入的js-->   '
                        domStr += '<object id="TANGER_OCX" classid="clsid:' + this.classidx64 + '" codebase="' + this.codebase64 + '" width="' + this.width + '" height="' + this.height + '">   '
                        domStr += '<param name="MakerCaption" value="' + this.MakerCaption + '">   '
                        domStr += '<param name="MakerKey" value="' + this.MakerKey + '">   '
                        domStr += '<param name="ProductCaption" value="' + this.ProductCaption + '">   '
                        domStr += '<param name="ProductKey" value="' + this.ProductKey + '">   '
                        domStr += '<param name="NoExpireKey" value="' + this.NoExpireKey + '">   '
                        domStr += '<param name="IsUseUTF8URL" value="-1">   '
                        domStr += '<param name="IsUseUTF8Data" value="-1">   '
                        domStr += '<param name="Caption" value="NTKO OFFICE文档控件示例演示 http://www.ntko.com">   '
                        domStr += '<SPAN STYLE="color:red">不能装载文档控件。请在检查浏览器的选项中检查浏览器的安全设置。</SPAN>   '
                        domStr += '</object>'
                        document.getElementById('ntko').innerHTML += domStr
                    }
                }
                else if (this.browser == "firefox") {
                    let domStr = ""
                    domStr += '<object id="TANGER_OCX" type="application/ntko-plug"  codebase="' + this.codebase + '" width="' + this.width + '" height="' + this.height + '" ForOnSaveToURL="ntkosavetourl"  ForOndocumentopened="ntkoondocumentopened"'
                    domStr += 'ForOnpublishAshtmltourl="ntkopublishashtml"'
                    domStr += 'ForOnpublishAspdftourl="ntkopublishaspdf"'
                    domStr += 'ForOnSaveAsOtherFormatToUrl="ntkosaveasotherurl"'
                    domStr += '_MakerCaption="' + this.MakerCaption + '"  '
                    domStr += '_MakerKey="' + this.MakerKey + '"  '
                    domStr += '_ProductCaption="' + this.ProductCaption + '"  '
                    domStr += '_ProductKey="' + this.ProductKey + '"   '
                    domStr += '_NoExpireKey="' + this.NoExpireKey + '"   '
                    domStr += 'clsid="{' + this.classid + '}" >'
                    domStr += '<SPAN STYLE="color:red">尚未安装NTKO Web FireFox跨浏览器插件</SPAN>   '
                    domStr += '</object>   '
                    document.getElementById('ntko').innerHTML += domStr
                } else if (this.browser == "chrome") {
                    let domStr = ""
                    domStr += '<object id="TANGER_OCX" clsid="{' + this.classid + '}"  ForOnSaveToURL="ntkosavetourl"  ForOndocumentopened="ntkoondocumentopened"'
                    domStr += 'ForOnpublishAshtmltourl="ntkopublishashtml"'
                    domStr += 'ForOnpublishAspdftourl="ntkopublishaspdf"'
                    domStr += 'ForOnSaveAsOtherFormatToUrl="ntkosaveasotherurl"'
                    domStr += '_IsUseUTF8URL="-1"   '
                    domStr += '_IsUseUTF8Data="-1"   '
                    domStr += '_MakerCaption="' + this.MakerCaption + '"  '
                    domStr += '_MakerKey="' + this.MakerKey + '"  '
                    domStr += '_ProductCaption="' + this.ProductCaption + '"  '
                    domStr += '_NoExpireKey="' + this.NoExpireKey + '"   '
                    domStr += '_ProductKey="' + this.ProductKey + '"   '
                    domStr += 'codebase="' + this.codebase + '" width="' + this.width + '" height="' + this.height + '" type="application/ntko-plug" '
                    domStr += '<SPAN STYLE="color:red">尚未安装NTKO Web Chrome跨浏览器插件</SPAN>   '
                    domStr += '</object>'
                    document.getElementById('ntko').innerHTML += domStr
                } else {
                    alert("暂不支持其他浏览器");
                }
                var obj = document.getElementById("TANGER_OCX");
                this.NTKOGLOBAL.TANGER_OCX_OBJ = obj;
                if(window.navigator.platform=="Win32"){
                    this.NTKOGLOBAL.TANGER_OCX_OBJ.AddDocTypePlugin(".pdf","PDF.NtkoDocument","4.0.2.0","officecontrol/ntkooledocall.cab",51,true);	
                }else{
                    this.NTKOGLOBAL.TANGER_OCX_OBJ.AddDocTypePlugin(".pdf","PDF.NtkoDocument","4.0.2.0","officecontrol/ntkooledocallx64.cab",51,true);	
                }
                let id = this.$route.query.id;
                let params = id;
                preview(params)
                    .then(res => {
                        console.log("预览", res.data);
                        if (res.data.status == 200) {
                            let pos = res.data.results.tag1.lastIndexOf('/');
                            let str = res.data.results.tag1.substr(0, pos);
                            let url;
                            if (res.data.results.tag2 != undefined) {
                                url = str + '/' + res.data.results.tag2;
                            } else if (res.data.results.documentno != undefined) {
                                url = str + '/' + res.data.results.documentno;
                            } else {
                                url = res.data.results.tag1;
                            }
                            if (id == "rule") {
                                this.NTKOGLOBAL.TANGER_OCX_OBJ.openfromurl(process.env.BASE_API + "/recommend/rule");
                            } else {
                                this.NTKOGLOBAL.TANGER_OCX_OBJ.openfromurl(url);
                            }
                        } else {
                            this.$message.error("获取预览数据失败");
                        }

                    })
                    .catch(error => {
                        console.error("error", error);
                    });
            })
            //
        }

    }
</script>