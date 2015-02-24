#!/usr/bin/perl

$argc = $#ARGV +2;

if($argc == 0)
{
	print "Usage: perl unify-db.pl dirpath [prefix]";
	print "\tdirpath - path to the directory containing documents";
	print "\tprefix - optional, the prefix to added to generated documents";
}

if($argc>0)
{
	$path = $ARGV[0];
	if(substr($path, -1) == "/")
	{
		chop $path;
	}
}
else
{
	$path = ".";
}

if($argc>1)
{
	$prefix = $ARGV[1];
}
else
{
	$prefix = "";
}

@files = <$path/*>;
$i = 0;

if(-d "$path/../unifiedDB")
{

}
else
{
	mkdir "$path/../unifiedDB";
}

foreach $file (@files) {
  	print "Parsing" . $file . "\n";

  	if (open(my $fh, '<', $file)) {

  		$document = "";

		while (my $row = <$fh>) {
			chomp $row;
		    $document .= "$row\n";
		}

		@matches = split(/<\/DOC>/, $document);

		pop @matches;

		foreach $item (@matches)
		{
			$text = "<?xml version=\"1.0\"?>\n" . $item . "\n</DOC>";
			open( $fh, ">", "$path/../unifiedDB/$prefix$i.xml") or die "Could not open file : $!";
			print $fh "$text";
			$i++;
		}

	} else {
	  	warn "Could not open file '$file' $!";
	}
}