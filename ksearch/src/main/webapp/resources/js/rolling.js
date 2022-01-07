var ScrollControl    = function(objId,options){
    this.init(objId,options);
};

ScrollControl.prototype    = {
    stage : {},
    options : {},
    isover : false,
    freeze : false,
    course : 'top',
    itimes : 0,
    top : 0,

    init : function(objId,options){
        this.setOptions(options);
        this.setStage(objId);
        this.setTrueData();

        this.move();
    },
    setOptions : function(options){
        options.inteval    = options.inteval    || 30;
        options.freeze    = options.freeze    || 1000;
        options.height    = parseInt(options.height, 10)    || 20;
        options.style    = options.style || 'scroll';
        options.line    = options.line || 1;
        options.stopline    = options.stopline || options.line;
        options.reverse    = options.reverse || '';
        options.debug    = options.debug || false;

        options.blockHeight    = options.height * options.line;
        options.stopHeight    = options.height * (options.stopline || options.line);

        options.cMarginTop    = 0;
        options.cHeight        = options.height - options.cMarginTop;
        options.freeze        = (options.style == 'no-freeze') ? options.inteval : options.freeze;

        switch(options.style){
            case 'jump' :        options.style = options.style;    break;
            default :            options.style = 'scroll';    break;
        }


        this.course        = options.start || 'top';
        this.options    = options;
    },
    setTrueData : function(){
        var cObjs        = this.stage.childNodes;
        var cObjLen        = cObjs.length;
        var removes        = [];

        for(var i=0;i<cObjs.length;i++){
            if(!cObjs[i].tagName){
                removes.push(cObjs[i]);    //바로 삭제 시 데이터 꼬이는듯 하여 일단 킵함.
            }else{
                cObjs[i].style.margin        = "0";

                cObjs[i].style.marginTop    = this.options.cMarginTop;
                cObjs[i].style.height        = this.options.cHeight+'px';
            }
        }

        for(var i=0;i<removes.length;i++){
            this.stage.removeChild(removes[i]);
        }

        if(!this.options.debug && cObjLen <= this.options.line ){
            var attachLen    = parseInt(this.options.line/cObjLen, 10);

            for(var i=0; i<attachLen; i++){
                for(var j=0; j<cObjLen; j++){
                    this.stage.appendChild( cObjs[j].cloneNode(true) );
                }
            }
        }

    },
    setStage : function(objId){
//    	console.log(objId);

        var This    = this;
        this.stage        = document.getElementById(objId);

//        console.log(  document.getElementById(objId));
        this.stage.style.height        = this.options.blockHeight+'px';
        this.stage.style.padding    = "0px";
        this.stage.style.margin        = "0px";

        if(!this.options.debug)
            this.stage.style.overflow    = "hidden";


        this.stage.onmouseover = function(){This.isover=true; document.getElementById("showpop").style.display = "block";};
        this.stage.onmouseout  = function(){This.isover=false; };

        this.stage.scrollTop        = 0;

    },
    changeChild : function(count){
        if(count == undefined){    count    = this.stage.childNodes.length - 1;}

        for(var i=0; i<count;i++ ){
            this.stage.appendChild( this.stage.childNodes[0] );    // 항상 첫번째것을 뒤로 이동
        }
    },
    actionTop : function(){
        if(this.options.style == 'jump'){
            this.changeChild(this.options.stopline);
            this.stage.scrollTop = 0;
            this.freeze            = true;
        }else{
            this.top++;
            this.stage.scrollTop++;

            if( this.stage.scrollTop >= this.options.height ){
                this.changeChild(1);
                this.stage.scrollTop = this.stage.scrollTop - this.options.height;

                //if문 안에 있는게 효율
                if( this.top >= this.options.stopHeight ){
                    this.top            = 0;
                    this.freeze            = true;
                    if(this.options.reverse == 'top' || this.options.reverse == 'all')    this.setCourse("down");
                }
            }
        }
    },
    actionDown : function(){
        if(this.options.style == 'jump'){
            this.freeze                = true;
            for(var i=0; i<this.options.stopline;i++){
                this.changeChild();
            }
        }else{
            this.top--;
            this.stage.scrollTop--;

            if( this.stage.scrollTop <= 0 ){
                this.changeChild();
                this.stage.scrollTop = this.stage.scrollTop + this.options.height;

                //Math.abs 로 구할 수 있지만, Math함수가 무거운 관계로 음수 처리함.(if문 안에 있는게 효율)
                if( -(this.top) >= this.options.stopHeight ){
                    for(var i=1; i<this.options.stopline;i++){
                        this.changeChild();
                    }

                    this.top            = this.top + this.options.stopHeight;
                    this.freeze            = true;
                }
            }
        }

        if(this.freeze){
            this.stage.scrollTop    = this.options.stopHeight;
            if(this.options.reverse == 'down' || this.options.reverse == 'all')    this.setCourse("top");
        }

    },
    move : function(){
        this.itimes    = 0;
        if(!this.isover){
            if(this.course == 'top')    this.actionTop();
            else                        this.actionDown();
        }

        var This    = this;
        if(this.freeze){    this.itimes    =        window.setTimeout(function(){This.move();}, this.options.freeze); }
        else{                window.setTimeout(function(){This.move();}, this.options.inteval); }
        this.freeze    = false;
    }

};
