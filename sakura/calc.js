formula = Editor.GetSelectedString(0);
if ( formula == '' ) {
	Editor.MoveHistSet();
	Editor.BeginSelect();
	Editor.GoLineTop_Sel();
	formula = Editor.GetSelectedString(0);
	Editor.Cut();
	Editor.Paste();
	Editor.MoveHistPrev();
}

formula = formula.replace(/[=\x00-\x20]+$/g, "").toLowerCase();
if ( (pos = formula.indexOf("=")) != -1 ) formula = formula.substring(pos+1);
formula = formula.replace(/[^0-9a-z\^\*\/\%+\-\(\)\.,]/ig, "");
formula = formula.replace(/(\-?[0-9\.]+)\^(\-?[0-9\.]+)/g, "Math.pow($1,$2)");
formula = formula.replace(/(abs|ceil|floor|max|min|round|acos|asin|atan|atan2|cos|exp|log|random|sin|sqrt|tan)\s*\(/g, "Math.$&");
formula = formula.replace(/pi\s*\(\s*\)/g, "3.1415926535897932");

var ans = new Number(eval(formula));
if ( formula != "" ) Editor.InsText(ans.toString());

