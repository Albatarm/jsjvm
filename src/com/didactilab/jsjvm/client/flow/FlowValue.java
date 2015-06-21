package com.didactilab.jsjvm.client.flow;

import com.didactilab.jsjvm.client.classfile.descriptor.DescType;

public interface FlowValue extends FlowObject {
	DescType getType();
}
