package fr.inria.jtravis;

import java.io.IOException;

public class Http404Exception extends IOException {
	public Http404Exception(String msg, Throwable t) {
		super(msg, t);
	}
}
