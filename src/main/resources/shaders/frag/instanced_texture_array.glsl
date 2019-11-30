#version 450

in vec3 aPosition;
in vec3 aNormal;
in vec2 aTexCoord;
in flat int layer;

uniform sampler2DArray tex;

out vec4 gl_FragColor;

void main() {
    gl_FragColor = texture(tex, vec3(aTexCoord.x, aTexCoord.y, layer));
}
