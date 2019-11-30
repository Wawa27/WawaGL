#version 450

struct Material {
    vec3 ambient;
    sampler2D ambient_texture;
    vec3 diffuse;
    vec3 specular;
    vec3 emissive;
    float density;
    float specular_exponent;
    float transparency;
    int illum;
};

in vec3 aPosition;
in vec3 aNormal;
in vec2 aTexCoord;

out vec4 gl_FragColor;

uniform Material material;

void main() {
    gl_FragColor = vec4(material.ambient, 1.0) * texture(material.ambient_texture, aTexCoord);
}
