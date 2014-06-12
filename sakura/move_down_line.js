(function() {

    var from = Editor.GetSelectLineFrom();
    var to = Editor.GetSelectLineTo();

    // ��s�I���i�s���̉��s�R�[�h�܂ށj
    if (from == 0 || to == 0) {
        Editor.GoLineTop(1);
        Editor.GoLineEnd_Sel();
        Editor.Right_Sel();
    }

    // ���ɍs�����݂��Ȃ��ꍇ�A�I������B
    if (from != 1 || to != 1) {
        return;
    }

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