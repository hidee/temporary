(function(){
    if(Editor.GetSelectColmFrom() == 0 || Editor.GetSelectColmTo() == 0){
        Editor.GoLineTop(1);
        Editor.GoLineEnd_Sel();
        Editor.Right_Sel();
    }

    if(Editor.GetSelectColmFrom() != 1 || Editor.GetSelectColmTo() != 1) return;

    var from = Editor.GetSelectLineFrom();
    var to = Editor.GetSelectLineTo();
    var height = (from < to) ? to - from : from - to;
    var dest = (from < to) ? to : from + 1;

    var a = Editor.GetSelectedString();
    var b = Editor.GetLineStr(dest);

    Editor.Jump(from, 1);
    for(var i = 0; i < height + 1; i++) Editor.Down_Sel();
    Editor.InsText(b + a);

    Editor.Jump(from + 1, 1);
    for(var i = 0; i < height; i++) Editor.Down_Sel();
})();