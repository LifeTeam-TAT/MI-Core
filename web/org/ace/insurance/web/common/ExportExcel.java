package org.ace.insurance.web.common;

import java.io.IOException;
import java.io.OutputStream;

public interface ExportExcel {
	public void generate(OutputStream op) throws IOException;
}
