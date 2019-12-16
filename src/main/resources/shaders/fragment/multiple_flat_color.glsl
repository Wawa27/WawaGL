#version 450

const int MAX_INSTANCE_COUNT = 256;

struct Material {
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
    vec3 emissive;
    float density;
    float specular_exponent;
    float transparency;
    int illum;
};

struct FlatColor {
    vec4 ambient;
    vec4 diffuse;
    vec4 specular;
    vec4 emissive;
};

in vec3 oPosition;
in vec3 oNormal;
in vec2 oTexCoord;
in flat int oInstanceID;

out vec4 gl_FragColor;

uniform Material material;
uniform FlatColor colors[MAX_INSTANCE_COUNT];

void main() {
    gl_FragColor = vec4(material.ambient, 1.0) * colors[oInstanceID].ambient;
}
