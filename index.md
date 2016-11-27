---
layout: page
title: Turkish PropBank
published: true
---
<script type="text/javascript" src="{{ site.baseurl }}/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="{{ site.baseurl }}/js/jquery.autocomplete.min.js"></script>
<script type="text/javascript" src="{{ site.baseurl }}/js/verb-autocomplete.js"></script>

Turkish Proposition Bank is the first semantically annotated corpus built for Turkish language. It is constructed upon [IMST](http://tools.nlp.itu.edu.tr/Datasets) and later mapped onto [IMST Universal Dependencies (UD)](http://universaldependencies.org/#tr). It contains sense and semantic argument information of each predicate similar to

![_config.yml]({{ site.baseurl }}/images/example_scaledDown.png)

_giy.01_ means the "1st" sense of predicate lemma _giy (wear)_ and labels A-A, A0 and A1 represents semantic arguments. Numbered arguments (A0-A4) of each predicate sense are registered in **frame files** and definitions of adjunct like arguments can be found in original [English PropBank project](http://propbank.github.io/).

### Frames

Frame files can be download from [here](https://github.com/turkishpropbank/turkishpropbank.github.io/tree/master/frames).

### Browse online

<div id="searchfield">					
<form><input type="text" name="verb" class="biginput" id="autocomplete"></form>
</div>
<p id="outputcontent"> </p>

Link to a page: [here]({{ site.baseurl }}/frames_web/aban.html)

[a relative link](/frames_web/aban.md)
### Annotated Corpora

Corpora constructed upon IMST and IMST-UD can be investigated from [project's corpus repo](https://github.com/turkishpropbank/turkishpropbank.github.io/tree/master/corpus). Please check the license and readme files before usage.

### Turkish Semantic Role Labeling


### License

We distribute resources built in scope of this project under [Creative Commons BY-NC-SA 4.0 International license](https://creativecommons.org/licenses/by-nc-sa/4.0/). 



### Publications
